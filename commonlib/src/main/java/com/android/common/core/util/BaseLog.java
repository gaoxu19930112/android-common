package com.android.common.core.util;

import android.util.Log;


/**
 * <pre>
 *     author : tangjy
 *     e-mail : jianye.tang@aorise.org
 *     time   : 2017/03/17
 *     desc   : 公共打印log接口
 *     version: 1.0
 * </pre>
 */
public class BaseLog {
    private static boolean sDebug = true;
    private static String sTagPrefix = "cn.aorise.common";


    private BaseLog() {
    }


    /**
     * DEBUG配置
     *
     * @param debug     是否打开debug开关
     * @param tagPrefix 打印log前缀
     */
    public static void init(boolean debug, String tagPrefix) {
        sDebug = debug;
        sTagPrefix = tagPrefix;
    }

    /**
     * The method prints the log, level error.
     *
     * @param tag the tag of the class.
     * @param msg the message to print.
     */
    public static void e(String tag, String msg) {
        if (sDebug) {
            Log.e(sTagPrefix, tag + ", " + msg);
        }
    }

    /**
     * The method prints the log, level error.
     *
     * @param tag the tag of the class.
     * @param msg the message to print.
     * @param t   an exception to log.
     */
    public static void e(String tag, String msg, Throwable t) {
        if (sDebug) {
            Log.e(sTagPrefix, tag + ", " + msg, t);
        }
    }

    /**
     * The method prints the log, level warning.
     *
     * @param tag the tag of the class.
     * @param msg the message to print.
     */
    public static void w(String tag, String msg) {
        if (sDebug) {
            Log.w(sTagPrefix, tag + ", " + msg);
        }
    }

    /**
     * The method prints the log, level warning.
     *
     * @param tag the tag of the class.
     * @param msg the message to print.
     * @param t   an exception to log.
     */
    public static void w(String tag, String msg, Throwable t) {
        if (sDebug) {
            Log.w(sTagPrefix, tag + ", " + msg, t);
        }
    }

    /**
     * The method prints the log, level debug.
     *
     * @param tag the tag of the class.
     * @param msg the message to print.
     */
    public static void i(String tag, String msg) {
        if (sDebug) {
            Log.i(sTagPrefix, tag + ", " + msg);
        }
    }

    /**
     * The method prints the log, level debug.
     *
     * @param tag the tag of the class.
     * @param msg the message to print.
     * @param t   an exception to log.
     */
    public static void i(String tag, String msg, Throwable t) {
        if (sDebug) {
            Log.i(sTagPrefix, tag + ", " + msg, t);
        }
    }

    /**
     * The method prints the log, level debug.
     *
     * @param tag the tag of the class.
     * @param msg the message to print.
     */
    public static void d(String tag, String msg) {
        if (sDebug) {
            Log.e(sTagPrefix, tag + ", " + msg);
        }
    }

    /**
     * The method prints the log, level debug.
     *
     * @param tag the tag of the class.
     * @param msg the message to print.
     * @param t   An exception to log.
     */
    public static void d(String tag, String msg, Throwable t) {
        if (sDebug) {
            Log.d(sTagPrefix, tag + ", " + msg, t);
        }
    }

    /**
     * The method prints the log, level debug.
     *
     * @param tag the tag of the class.
     * @param msg the message to print.
     */
    public static void v(String tag, String msg) {
        if (sDebug) {
            Log.v(sTagPrefix, tag + ", " + msg);
        }
    }

    /**
     * The method prints the log, level debug.
     *
     * @param tag the tag of the class.
     * @param msg the message to print.
     * @param t   An exception to log.
     */
    public static void v(String tag, String msg, Throwable t) {
        if (sDebug) {
            Log.v(sTagPrefix, tag + ", " + msg, t);
        }
    }

    public static void printTrace(String tag) {
        if (sDebug) {
            Log.v(sTagPrefix, tag + ", Trace start");
            Thread.dumpStack();
            Log.v(sTagPrefix, tag + ", Trace end");
        }
    }

    public static void e(String msg) {
        if (sDebug) {
            Log.e(sTagPrefix, msg);
        }
    }

    public static void e(String msg, Throwable t) {
        if (sDebug) {
            Log.e(sTagPrefix, msg, t);
        }
    }

    public static void w(String msg) {
        if (sDebug) {
            Log.w(sTagPrefix, msg);
        }
    }

    public static void w(String msg, Throwable t) {
        if (sDebug) {
            Log.w(sTagPrefix, msg, t);
        }
    }

    public static void i(String msg) {
        if (sDebug) {
            Log.i(sTagPrefix, msg);
        }
    }

    public static void i(String msg, Throwable t) {
        if (sDebug) {
            Log.i(sTagPrefix, msg, t);
        }
    }

    public static void d(String msg) {
        if (sDebug) {
            Log.e(sTagPrefix, msg);
        }
    }

    public static void d(String msg, Throwable t) {
        if (sDebug) {
            Log.d(sTagPrefix, msg, t);
        }
    }

    public static void v(String msg) {
        if (sDebug) {
            Log.v(sTagPrefix, msg);
        }
    }

    public static void v(String msg, Throwable t) {
        if (sDebug) {
            Log.v(sTagPrefix, msg, t);
        }
    }
}