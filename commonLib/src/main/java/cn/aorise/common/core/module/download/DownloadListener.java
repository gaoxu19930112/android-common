package cn.aorise.common.core.module.download;

/**
 * <pre>
 *     author : gaoxu
 *     e-mail : 511527070@qq.com
 *     time   : 2018/10/12
 *     desc   : TODO
 *     version: 1.0
 * </pre>
 */
public interface DownloadListener {

    /**
     * 任务准备中，在任务队列中暂时未开始
     */
    void onReady();
    void onStart();
    void onNext(DownloadTask task);
    void onFailed();
    void onComplete();
    void onProgress(long readLength, long totalSize);
    void onPause();
    void onCancel();
}
