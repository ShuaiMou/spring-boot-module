package com.unimelb.saul.studySpringbootMybatisRedis.service.serviceImpl;


import com.unimelb.saul.studySpringbootMybatisRedis.domain.JsonData;
import com.unimelb.saul.studySpringbootMybatisRedis.domain.User;
import com.unimelb.saul.studySpringbootMybatisRedis.enumClass.StateType;
import com.unimelb.saul.studySpringbootMybatisRedis.mapper.UserMapper;
import com.unimelb.saul.studySpringbootMybatisRedis.service.UserService;
import com.unimelb.saul.studySpringbootMybatisRedis.utils.RedisUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
               System.out.println("user from MySQL");

               //数据库查询到就刷新redis缓存
               if (user != null){
                   redisUtils.set(key+email,user);
               }
           }
           return JsonData.buildSuccess(user);
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

    @Override
    public JsonData updatePersonalInfo(String email, String password) {
        try {
            return JsonData.buildSuccess(userMapper.updateInformation(email, password));
        }catch (Exception e){
            return JsonData.buildError(StateType.PROCESSING_EXCEPTION.getCode(), "数据库查询错误");
        }
    }
}
