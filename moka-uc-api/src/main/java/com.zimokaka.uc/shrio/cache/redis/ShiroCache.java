package com.zimokaka.uc.shrio.cache.redis;

import com.zimokaka.uc.shrio.constant.RedisConstant;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class ShiroCache<K, V> implements Cache<K, V> {

    private static final String REDIS_SHIRO_CACHE =RedisConstant.SHIRO_CACHE;
    private String cacheKey;
    private RedisTemplate<K, V> redisTemplate;
    private long globExpire = 1800;

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public ShiroCache(String name) {
        this.cacheKey = REDIS_SHIRO_CACHE+":"+ name + ":";
    }
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public ShiroCache(String name, RedisTemplate redisTemplate) {
        this.cacheKey = REDIS_SHIRO_CACHE+":"+ name + ":";
        this.redisTemplate = redisTemplate;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public ShiroCache(String name, RedisTemplate redisTemplate,long globExpire) {
        this.cacheKey = REDIS_SHIRO_CACHE+":"+ name + ":";
        this.redisTemplate = redisTemplate;
        this.globExpire=globExpire;
    }

    @Override
    public V get(K key) throws CacheException {
        redisTemplate.boundValueOps(getCacheKey(key)).expire(globExpire, TimeUnit.MINUTES);
        return redisTemplate.boundValueOps(getCacheKey(key)).get();
    }

    @Override
    public V put(K key, V value) throws CacheException {
        V old = get(key);
        redisTemplate.boundValueOps(getCacheKey(key)).set(value);
        return old;
    }

    @Override
    public V remove(K key) throws CacheException {
        V old = get(key);
        redisTemplate.delete(getCacheKey(key));
        return old;
    }

    @Override
    public void clear() throws CacheException {
        redisTemplate.delete(keys());
    }

    @Override
    public int size() {
        return keys().size();
    }

    @Override
    public Set<K> keys() {
        return redisTemplate.keys(getCacheKey("*"));
    }

    @Override
    public Collection<V> values() {
        Set<K> set = keys();
        List<V> list = new ArrayList<V>();
        for (K s : set) {
            list.add(get(s));
        }
        return list;
    }

    @SuppressWarnings("unchecked")
    private K getCacheKey(Object k) {
        return (K) (this.cacheKey + k);
    }
}
