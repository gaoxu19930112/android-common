package com.android.common.core.util;

import android.content.Context;
import android.os.storage.StorageManager;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <pre>
 *     author : gaoxu
 *     e-mail : 511527070@qq.com
 *     time   : 2018/10/12
 *     desc   : SD 卡相关工具类
 *     version: 1.0
 * </pre>
 */
public final class SDCardUtils {

    private SDCardUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 判断外置 SD 卡是否可用
     *
     * @return true : 可用<br>false : 不可用
     */
    public static boolean isSDCardEnable() {
        return !getSDCardPaths().isEmpty();
        // return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }


//    /**
//     * 获取手机 data 路径.
//     *
//     * @return String
//     */
//    public static String getDataPath() {
//        return Environment.getDataDirectory().getPath();
//
//    }
//
//    /**
//     * 获取手机外置 SD 卡路径.
//     *
//     * @return String
//     */
//    public static String getExternalSDCardPath() {
//        return Environment.getExternalStorageDirectory().getPath();
//    }

    /**
     * 获取 SD 卡路径
     *
     * @param removable true : 外置 SD 卡<br>false : 内置 SD 卡
     * @return SD 卡路径
     */
    @SuppressWarnings("TryWithIdenticalCatches")
    public static List<String> getSDCardPaths(final boolean removable) {
        List<String> paths = new ArrayList<>();
        StorageManager sm =
                (StorageManager) Utils.getApp().getSystemService(Context.STORAGE_SERVICE);
        try {
            Class<?> storageVolumeClazz = Class.forName("android.os.storage.StorageVolume");
            Method getVolumeList = StorageManager.class.getMethod("getVolumeList");
            Method getPath = storageVolumeClazz.getMethod("getPath");
            Method isRemovable = storageVolumeClazz.getMethod("isRemovable");
            Object result = getVolumeList.invoke(sm);
            final int length = Array.getLength(result);
            for (int i = 0; i < length; i++) {
                Object storageVolumeElement = Array.get(result, i);
                String path = (String) getPath.invoke(storageVolumeElement);
                boolean res = (Boolean) isRemovable.invoke(storageVolumeElement);
                if (removable == res) {
                    paths.add(path);
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return paths;
    }

    /**
     * 获取 SD 卡路径
     *
     * @return SD 卡路径
     */
    @SuppressWarnings("TryWithIdenticalCatches")
    public static List<String> getSDCardPaths() {
        StorageManager storageManager = (StorageManager) Utils.getApp()
                .getSystemService(Context.STORAGE_SERVICE);
        List<String> paths = new ArrayList<>();
        try {
            Method getVolumePathsMethod = StorageManager.class.getMethod("getVolumePaths");
            getVolumePathsMethod.setAccessible(true);
            Object invoke = getVolumePathsMethod.invoke(storageManager);
            paths = Arrays.asList((String[]) invoke);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return paths;
    }
}
