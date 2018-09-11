package cn.aorise.common.core.config;


import cn.aorise.common.core.constant.MemoryConstants;

/**
 * <pre>
 *     author : tangjy
 *     e-mail : jianye.tang@aorise.org
 *     time   : 2017/03/17
 *     desc   : 全局常量配置
 *     version: 1.0
 * </pre>
 */
public class AoriseConfig {
    private static final int MAX_HEAP_SIZE = (int) Runtime.getRuntime().maxMemory();
    public static final int MAX_DISK_CACHE_SIZE = (int) (40 * MemoryConstants.MB);
    public static final int MAX_MEMORY_CACHE_SIZE = MAX_HEAP_SIZE / 8;

    public static final String CACHE_SP_NAME = "sp_cache";      //默认SharedPreferences缓存目录
    public static final String CACHE_DISK_DIR = "disk_cache";   //默认磁盘缓存目录
    public static final String CACHE_HTTP_DIR = "http_cache";   //默认HTTP缓存目录

    public static final int MAX_AGE_ONLINE = 60;            //默认最大在线缓存时间（秒）
    public static final int MAX_AGE_OFFLINE = 24 * 60 * 60; //默认最大离线缓存时间（秒）

    public static String API_HOST = "https://api.github.com/";  //默认API主机地址

    public static final String COOKIE_PREFS = "Cookies_Prefs";  //默认Cookie缓存目录

    public static final int DEFAULT_TIMEOUT = 60;               //默认超时时间
    public static final int DEFAULT_MAX_IDLE_CONNECTIONS = 5;   //默认空闲连接数
    public static final long DEFAULT_KEEP_ALIVE_DURATION = 8;   //默认心跳间隔时长
    public static final long CACHE_MAX_SIZE = 10 * 1024 * 1024; //默认最大缓存大小


    /**
     * 统一请求CODE
     */
    public static final int REQUEST_CODE = 1000;

    /**
     * 渠道标志
     */
    public static final String CHANNEL = "UMENG_CHANNEL";

    /**
     * 推送的key
     */
    public static final String PUSH_KEY = "PUSH_KEY";

    /**
     * T卡文件夹
     */
    public static class Folder {
        /**
         * LOG目录
         */
        public static final String LOG_PATH = "log";
        /**
         * 缓存目录
         */
        public static final String CACHE_PATH = "cache";
        /**
         * 下载目录
         */
        public static final String DOWNLOAD_PATH = "download";
    }

    /**
     * 透传关键字
     */
    public static class TransportKey {
        // 导航页
        public static final String GUIDE_KEY = "guide_key";
        // intent关键字
        public static final String INTENT_KEY = "intent_key";
        // bundle关键字
        public static final String BUNDLE_KEY = "bundle_key";
        // URL
        public static final String BUNDLE_URL = "bundle_url_key";
        // 标题
        public static final String BUNDLE_TITLE = "bundle_title";
    }

    /**
     * 广播action
     */
    public static class BroadcastKey {
        // 登录后的用户信息
        public static final String CN_AORISE_PLATFORM_LOGIN_ACCOUNT = "cn.aorise.platform.login.ACCOUNT";
    }

    /**
     * 用户信息
     */
    public static class AccountKey {
        public static final String USER_ACCOUNT = "user_account";
        public static final String USER_ID = "user_id";
        public static final String USER_SEX = "user_sex";
    }
}
