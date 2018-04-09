package com.zhangxi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

@Component
public class RedisService {

    private RedisTemplate redisTemplate;

    @Autowired(required = false)
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        RedisSerializer redisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(redisSerializer);
        redisTemplate.setValueSerializer(redisSerializer);
        redisTemplate.setHashKeySerializer(redisSerializer);
        redisTemplate.setHashValueSerializer(redisSerializer);
        this.redisTemplate = redisTemplate;
    }

    public boolean set(final String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public String get(final String key) {
        Object o = redisTemplate.opsForValue().get(key);
        return o != null ? o.toString() : "";
    }

    public String hget(final String key, final String field) {
        Object o = redisTemplate.opsForHash().get(key, field);
        return o != null ? o.toString() : "";
    }

}
