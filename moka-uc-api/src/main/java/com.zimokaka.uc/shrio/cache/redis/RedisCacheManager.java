package com.zimokaka.uc.shrio.cache.redis;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author liao
 * @Time 2017/8/8
 */
@Component
public class RedisCacheManager implements CacheManager {


    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        return new ShiroCache<K, V>(name, redisTemplate);
    }

    public RedisTemplate<String, ?> getRedisTemplate() {
        return redisTemplate;
    }
    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
}
