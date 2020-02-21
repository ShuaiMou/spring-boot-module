package com.unimelb.saul.studySpringbootMybatisRedis.controller;

import com.unimelb.saul.studySpringbootMybatisRedis.domain.JsonData;
import com.unimelb.saul.studySpringbootMybatisRedis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: Saul
 * @Date: 22/2/20 10:01 AM
 * @Description:
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;


    @GetMapping("/login")
    public JsonData checkLogin(String email, String password) {
        return userService.checkLogin(email, password);
    }

    @GetMapping("/register")
    public JsonData register(String email, String password, String username) {
        return userService.register(email, password, username);
    }

    @GetMapping("/update")
    public JsonData updatePersonalInfo(String email, String password) {
        return userService.updatePersonalInfo(email, password);
    }


}
