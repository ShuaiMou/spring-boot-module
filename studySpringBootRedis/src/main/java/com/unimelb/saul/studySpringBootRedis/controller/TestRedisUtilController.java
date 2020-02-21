package com.unimelb.saul.studySpringBootRedis.controller;

import com.unimelb.saul.studySpringBootRedis.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: Saul
 * @Date: 21/2/20 8:40 PM
 * @Description:
 */
@RestController
@RequestMapping("/redis")
public class TestRedisUtilController {

    @Autowired
    private RedisUtils redisUtils;

    @GetMapping("/string")
    public Object testString(){
        redisUtils.set("name", "saul");
        return redisUtils.get("name");
    }

    @GetMapping("/hash")
    public Object testHash(){
        redisUtils.hset("name1", "age", 20);
        return redisUtils.hget("name1", "age");

    }

    @GetMapping("/list")
    public Object testList(){
        redisUtils.lpush("list", "xiaolin");
        return redisUtils.lpop("list");

    }

    @GetMapping("/set")
    public Object testset(){
        redisUtils.sadd("set", "a","b","c");
        return redisUtils.smembers("set");

    }

    @GetMapping("/sortSet")
    public Object testSortSet(){
        redisUtils.zadd("zset", "name", 20);
        redisUtils.zadd("zset", "huahau", 25);
        redisUtils.zadd("zset", "hehe", 18);
        return redisUtils.zrangebyscore("zset", 10, 90);

    }
}
