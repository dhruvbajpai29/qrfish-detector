package com.safeqr.qrfish_detector.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;


//kept file redundant as of now to check if postgres or redis is failing connection to the db
@Service
public class RedisBlacklistService {

    private static final String PREFIX="blacklisted";

    @Autowired
    private StringRedisTemplate redisTemplate;

    public boolean isBlackListed(String url){
        return Boolean.TRUE.equals(redisTemplate.hasKey(PREFIX+url));
    }

    public void blacklistUrl(String url){
        redisTemplate.opsForValue().set(PREFIX+url,"true",24, TimeUnit.HOURS);
    }
}
