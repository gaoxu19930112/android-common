package com.android.common.core.module.download;

import android.util.Log;

import com.android.common.core.module.database.DbHelper;
import com.android.common.core.module.multilang.DownInfoDao;
import com.android.common.core.network.CommonAPIService;
import com.android.common.core.util.UrlUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * <pre>
 *     author : gaoxu
 *     e-mail : 511527070@qq.com
 *     time   : 2018/10/12
 *     desc   : TODO
 *     version: 1.0
 * </pre>
 */
public class DownloadManager {

    private static final String TAG = DownloadManager.class.getSimpleName();
    private static volatile DownloadManager mInstance;
    private int maxParallelRunningCount = 2;
    private HashMap<DownloadTask, Disposable> loadingMap = new HashMap<>(maxParallelRunningCount);
    private List<DownloadTask> readyTask = new ArrayList<>();
    private DownInfoDao mDownInfoDao;

    private DownloadManager() {
        mDownInfoDao = DbHelper.getInstance().getDaoSession().getDownInfoDao();
    }

    public static DownloadManager getInstance() {
        if (mInstance == null) {
            synchronized (DownloadManager.class) {
                if (mInstance == null) {
                    mInstance = new DownloadManager();
                }
            }
        }
        return mInstance;
    }

    public void setMaxParallelRunningCount(int maxParallelRunningCount) {
        if (maxParallelRunningCount > 10 || maxParallelRunningCount < 1) {
            throw new IllegalStateException("maxParallelRunningCount not <0 or >10");
        }
        this.maxParallelRunningCount = maxParallelRunningCount;
    }

    synchronized void enqueue(@NonNull DownloadTask task) {
        if (inspectCompleted(task)) return;
        if (inspectForConflict(task)) return;

        final int originReadyAsyncCallSize = readyTask.size();
        enqueueIgnorePriority(task);
        if (originReadyAsyncCallSize != readyTask.size()) Collections.sort(readyTask);
    }

    private synchronized void enqueueIgnorePriority(@NonNull DownloadTask task) {
        if (loadingMap.size() < maxParallelRunningCount) {
            startDown(task);
        } else {
            readyTask.add(task);
            task.setState(DownState.READY);
            if (task.getDownloadListener() != null) {
                task.getDownloadListener().onReady();
            }
        }
    }

    private boolean inspectForConflict(@NonNull DownloadTask task) {
        return readyTask.contains(task) || loadingMap.containsKey(task);
    }

    private boolean inspectCompleted(@NonNull DownloadTask task) {
        DownInfo info = getDBTaskInfo(task);
        if (info != null && task.getReadLength() == info.getTotalSize()) {
            DownloadListener listener = task.getDownloadListener();
            if (listener != null) {
                listener.onProgress(100, 100);
                listener.onComplete();
            }
            return true;
        }
        return false;
    }

    private void startDown(@NonNull DownloadTask task) {
        DownloadListener listener = task.getDownloadListener();
        long readLength = task.getReadLength();
        CommonAPIService.Factory.create(UrlUtils.getBasUrl(task.getUrl()))
                .download("bytes=" + readLength + "-", task.getUrl())
                /*指定线程*/
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .map(new Function<ResponseBody, DownloadTask>() {
                    @Override
                    public DownloadTask apply(ResponseBody responseBody) throws Exception {
                        Log.e(TAG, "apply: " + responseBody.contentLength());
                        DownloadFileUtils.writeFile(task, responseBody);
                        return task;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DownloadTask>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        loadingMap.put(task, d);
                        if (listener != null) {
                            listener.onStart();
                        }
                        task.setState(DownState.DOWN);
                        savaOrUpdate(task);
                    }

                    @Override
                    public void onNext(DownloadTask downloadTask) {
                        if (listener != null) {
                            listener.onNext(downloadTask);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingMap.remove(task);
                        processReadyTask();
                        if (listener != null) {
                            listener.onFailed();
                        }
                        task.setState(DownState.ERROR);
                        savaOrUpdate(task);
                    }

                    @Override
                    public void onComplete() {
                        loadingMap.remove(task);
                        processReadyTask();
                        if (listener != null) {
                            listener.onComplete();
                        }
                        task.setState(DownState.FINISH);
                        savaOrUpdate(task);
                    }
                });
    }

    private synchronized void processReadyTask() {
        if (readyTask.isEmpty()) return;
        for (Iterator<DownloadTask> i = readyTask.iterator(); i.hasNext(); ) {
            if (loadingMap.size() >= maxParallelRunningCount) return;
            DownloadTask task = i.next();
            i.remove();
            startDown(task);
        }
    }

    synchronized void onPause(@NonNull DownloadTask task) {
        if (readyTask.contains(task)) {
            readyTask.remove(task);
            return;
        }
        Disposable disposable = loadingMap.remove(task);
        if (disposable != null) {
            disposable.dispose();
        }
        Log.e(TAG, "onPause: " + task.getReadLength());
        task.setState(DownState.PAUSE);
        savaOrUpdate(task);
        if (task.getDownloadListener() != null) {
            task.getDownloadListener().onPause();
        }
        processReadyTask();
    }

    synchronized void cancel(@NonNull DownloadTask task) {
        if (readyTask.contains(task)) {
            readyTask.remove(task);
            return;
        }
        Disposable disposable = loadingMap.remove(task);
        if (disposable != null) {
            disposable.dispose();
        }
        DownloadFileUtils.deleteFile(task.getSaveFile());
        mDownInfoDao.delete(task.getDownInfo());
        if (task.getDownloadListener() != null) {
            task.getDownloadListener().onCancel();
        }
        processReadyTask();
    }

    private void savaOrUpdate(@NonNull DownloadTask task) {
        mDownInfoDao.insertOrReplace(task.getDownInfo());
    }

    public List<DownloadTask> getLoadingList() {
        List<DownloadTask> downloadTasks = new ArrayList<>();
        List<DownInfo> downInfos = mDownInfoDao.queryBuilder().where(DownInfoDao.Properties.StateInte.notEq(DownState.FINISH.getState())).build().list();
        for (DownInfo info : downInfos) {
            DownloadTask task = new DownloadTask(info);
            downloadTasks.add(task);
        }
        return downloadTasks;
    }

    public List<DownloadTask> getFinishList() {
        List<DownloadTask> downloadTasks = new ArrayList<>();
        List<DownInfo> downInfos = mDownInfoDao.queryBuilder().where(DownInfoDao.Properties.StateInte.eq(DownState.FINISH.getState())).build().list();
        ;
        for (DownInfo info : downInfos) {
            DownloadTask task = new DownloadTask(info);
            downloadTasks.add(task);
        }
        return downloadTasks;
    }

    public List<DownloadTask> getAllList() {
        List<DownloadTask> downloadTasks = new ArrayList<>();
        List<DownInfo> downInfos = mDownInfoDao.loadAll();
        for (DownInfo info : downInfos) {
            DownloadTask task = new DownloadTask(info);
            downloadTasks.add(task);
        }
        return downloadTasks;
    }

    public boolean contains(@NonNull DownloadTask task) {
        return getDBTaskInfo(task) != null;
    }

    public DownInfo getDBTaskInfo(@NonNull DownloadTask task) {
        return mDownInfoDao.queryBuilder().where(DownInfoDao.Properties.Url.eq(task.getUrl())).unique();
    }

}
