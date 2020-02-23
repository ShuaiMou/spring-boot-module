package com.unimelb.saul.studySpringbootMybatisRedis.service.serviceImpl;


import com.unimelb.saul.studySpringbootMybatisRedis.domain.JsonData;
import com.unimelb.saul.studySpringbootMybatisRedis.domain.User;
import com.unimelb.saul.studySpringbootMybatisRedis.enumClass.StateType;
import com.unimelb.saul.studySpringbootMybatisRedis.mapper.UserMapper;
import com.unimelb.saul.studySpringbootMybatisRedis.service.UserService;
import com.unimelb.saul.studySpringbootMybatisRedis.utils.RedisUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@CacheConfig(cacheNames = "userCache")
@Service("userService")
public class UserServiceImpl implements UserService {

    private static final String key = "userCache:";

    @Resource
    private UserMapper userMapper;

    @Resource
    private RedisUtils redisUtils;


    @Override
    public JsonData checkLogin(String email, String password) {
       try {
           //先从redis里面取
           User user = (User) redisUtils.get(key + email);

           //缓存不存在就查询数据库
           if (user == null){
               user = userMapper.findByEmail(email);

               //数据库查询到就刷新redis缓存
               if (user != null){
                   redisUtils.set(key+email,user,10);
               }
               return JsonData.buildSuccess(user, "取自mysql");
           }
           return JsonData.buildSuccess(user, "取自缓存,有效时间" + redisUtils.getExpire(key+email));
        }catch (Exception e){
            return JsonData.buildError(StateType.PROCESSING_EXCEPTION.getCode(), "数据库查询错误");
        }
    }

    @Override
    public JsonData register(String email, String password, String username) {
        User user = new User(email,password,username);
        try {
            userMapper.addUser(user);
            return JsonData.buildSuccess(user);
        }catch (Exception e){
            return JsonData.buildError(StateType.PROCESSING_EXCEPTION.getCode(), "数据库查询错误");
        }
    }


    @CachePut(key = "#p0")
    @Override
    public User updatePersonalInfo(String email, String username) {
        userMapper.updateInformation(email, username);
        return userMapper.findByEmail(email);
    }

    @Cacheable(key = "#p0", unless = "#result == null")
    @Override
    public User findByEmail(String email) {
        return userMapper.findByEmail(email);
    }

    @CacheEvict(key = "#p0")
    @Override
    public User DeleteUser(String email) {
        User user = findByEmail(email);
        int flag = userMapper.deleteUser(email);
        if (flag == 1)
            return user;
        return null;
    }
}
