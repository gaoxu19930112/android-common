package com.android.common.core.util;

import android.content.Context;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


/**
 * <pre>
 *     author : tangjy
 *     e-mail : jianye.tang@aorise.org
 *     time   : 2017/03/17
 *     desc   : 资源文件处理工具类
 *     version: 1.0
 * </pre>
 */
public class ResourceUtils {

    private ResourceUtils() {
        throw new AssertionError();
    }

    /**
     * 读取 "Assets" 文件夹里面的文件内容
     *
     * @param context  上下文
     * @param fileName The name of the asset to open. This name can be hierarchical.
     * @return
     */
    public static String geFileFromAssets(Context context, String fileName) {
        if (context == null || TextUtils.isEmpty(fileName)) {
            return null;
        }

        StringBuilder s = new StringBuilder();
        InputStreamReader in = null;
        BufferedReader br = null;
        try {
            in = new InputStreamReader(context.getResources().getAssets().open(fileName));
            br = new BufferedReader(in);
            String line;
            while ((line = br.readLine()) != null) {
                s.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != in) {
                    in.close();
                }
                if (null != br) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return s.toString();
    }

    /**
     * 读取 "Raw" 文件夹里面的文件内容
     *
     * @param context 上下文
     * @param resId   The resource identifier to open, as generated by the appt
     *                tool.
     * @return
     */
    public static String geFileFromRaw(Context context, int resId) {
        if (context == null) {
            return null;
        }

        StringBuilder s = new StringBuilder();
        InputStreamReader in = null;
        BufferedReader br = null;
        try {
            in = new InputStreamReader(context.getResources().openRawResource(resId));
            br = new BufferedReader(in);
            String line;
            while ((line = br.readLine()) != null) {
                s.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != in) {
                    in.close();
                }
                if (null != br) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return s.toString();
    }

    /**
     * 读取 "Assets" 文件夹里面的文件内容
     *
     * @param context  上下文
     * @param fileName 文件名
     * @return
     */
    public static List<String> geFileToListFromAssets(Context context, String fileName) {
        if (context == null || TextUtils.isEmpty(fileName)) {
            return null;
        }

        List<String> fileContent = new ArrayList<String>();
        InputStreamReader in = null;
        BufferedReader br = null;
        try {
            in = new InputStreamReader(context.getResources().getAssets().open(fileName));
            br = new BufferedReader(in);
            String line;
            while ((line = br.readLine()) != null) {
                fileContent.add(line);
            }
            br.close();
            in.close();
            return fileContent;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (null != in) {
                    in.close();
                }
                if (null != br) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 读取 "Raw" 文件夹里面的文件内容
     *
     * @param context 上下文
     * @param resId   资源ID
     * @return
     */
    public static List<String> geFileToListFromRaw(Context context, int resId) {
        if (context == null) {
            return null;
        }

        List<String> fileContent = new ArrayList<String>();
        InputStreamReader in = null;
        BufferedReader br = null;
        try {
            in = new InputStreamReader(context.getResources().openRawResource(resId));
            br = new BufferedReader(in);
            String line = null;
            while ((line = br.readLine()) != null) {
                fileContent.add(line);
            }
            br.close();
            in.close();
            return fileContent;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (null != in) {
                    in.close();
                }
                if (null != br) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
