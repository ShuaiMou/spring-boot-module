package com.unimelb.saul.studySpringbootMybatisRedis.service;


import com.unimelb.saul.studySpringbootMybatisRedis.domain.JsonData;
import com.unimelb.saul.studySpringbootMybatisRedis.domain.User;

public interface UserService {
    JsonData checkLogin(String email, String password);
    JsonData register(String email, String password, String username);
    User updatePersonalInfo(String email, String username);
    User findByEmail(String email);
    User DeleteUser(String email);
}
