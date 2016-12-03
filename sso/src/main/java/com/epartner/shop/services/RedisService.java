package com.epartner.shop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * Created by martin on 16/11/16.
 */
@Service
public class RedisService {

    @Autowired
    private RedisTemplate< String, Object > template;

    public Object getValue(final String key) {
        return template.opsForValue().get(key);
    }

    public void setValue(final String key, final String value) {
        template.opsForValue().set(key, value);
        // set a expire for a message
        template.expire(key, 1, TimeUnit.DAYS);

    }

    public void deleteValue(final String key){
        template.delete(key);
    }
}
