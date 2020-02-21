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

    @Resource
    private UserMapper userMapper;

    @Resource
    private RedisUtils redisUtils;


    @Override
    public JsonData checkLogin(String email, String password) {
       try {
           return JsonData.buildSuccess(userMapper.findByEmail(email));
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
