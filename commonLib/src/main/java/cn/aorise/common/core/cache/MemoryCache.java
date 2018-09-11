package cn.aorise.common.core.cache;

import android.text.TextUtils;
import android.util.LruCache;

/**
 * <pre>
 *     author : tangjy
 *     e-mail : jianye.tang@aorise.org
 *     time   : 2017/03/17
 *     desc   : 内存缓存
 *     version: 1.0
 * </pre>
 */
public class MemoryCache implements ICache {
    private LruCache<String, Object> cache;
    private static MemoryCache sInstance;

    private MemoryCache() {
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemory / 8;
        cache = new LruCache(cacheSize);

    }

    public static MemoryCache getsInstance() {
        if (sInstance == null) {
            synchronized (MemoryCache.class) {
                if (sInstance == null) {
                    sInstance = new MemoryCache();
                }
            }
        }
        return sInstance;
    }

    @Override
    public synchronized void put(String key, Object value) {
        if (TextUtils.isEmpty(key)) return;

        if (cache.get(key) != null) {
            cache.remove(key);
        }
        cache.put(key, value);
    }

    @Override
    public Object get(String key) {
        return cache.get(key);
    }

    public synchronized <T> T get(String key, Class<T> clazz) {
        try {
            return (T) cache.get(key);
        } catch (Exception e) {
            e.printStackTrace();
            // AoriseLog.e(e);
        }
        return null;
    }

    @Override
    public void remove(String key) {
        if (cache.get(key) != null) {
            cache.remove(key);
        }
    }

    @Override
    public boolean contains(String key) {
        return cache.get(key) != null;
    }

    @Override
    public void clear() {
        cache.evictAll();
    }
}
