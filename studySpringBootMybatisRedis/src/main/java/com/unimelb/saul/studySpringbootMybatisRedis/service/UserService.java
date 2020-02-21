package com.unimelb.saul.studySpringbootMybatisRedis.service;


import com.unimelb.saul.studySpringbootMybatisRedis.domain.JsonData;

public interface UserService {
    JsonData checkLogin(String email, String password);
    JsonData register(String email, String password, String username);
    JsonData updatePersonalInfo(String email, String password);
}
