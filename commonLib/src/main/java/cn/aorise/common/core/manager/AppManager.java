package cn.aorise.common.core.manager;

import java.util.ArrayList;
import java.util.List;

import cn.aorise.common.core.interfaces.IAppCycle;


/**
 * <pre>
 *     author : tangjy
 *     e-mail : jianye.tang@aorise.org
 *     time   : 2017/03/17
 *     desc   : 插件APP生命周期管理
 *     version: 1.0
 * </pre>
 */
public class AppManager {
    private static AppManager sInstance;
    private List<IAppCycle> sList;

    private AppManager() {
        sList = new ArrayList<>();
    }

    public static AppManager getInstance() {
        if (sInstance == null) {
            synchronized (AppManager.class) {
                if (sInstance == null) {
                    sInstance = new AppManager();
                }
            }
        }
        return sInstance;
    }

    /**
     * 平台注册插件APP
     *
     * @param appCycle 插件APP对象
     */
    public void add(IAppCycle appCycle) {
        sList.add(appCycle);
    }

    /**
     * 平台卸载插件APP
     *
     * @param appCycle 插件APP对象
     */
    public void remove(IAppCycle appCycle) {
        sList.remove(appCycle);
    }

    /**
     * 清空平台注册的所有插件APP
     */
    public void clear() {
        sList.clear();
    }

    /**
     * 获取平台注册的所有插件APP列表
     */
    public List<IAppCycle> getList() {
        return sList;
    }


//    /**
//     * 平台初始化全部注册的插件APP
//     *
//     * @param context 插件APP对象
//     */
//    public void createAll(IAppCycle context) {
//        for (IAppCycle appCycle : sList) {
//            appCycle.create(context);
//        }
//    }
//
//    /**
//     * 平台释放全部注册的插件APP
//     *
//     * @param context       插件APP对象
//     * @param isKillProcess 为true强制退出插件APP
//     */
//    public void destroyAll(IAppCycle context, boolean isKillProcess) {
//        for (IAppCycle appCycle : sList) {
//            appCycle.destroy(context, isKillProcess);
//        }
//    }
}
