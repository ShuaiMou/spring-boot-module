package com.unimelb.saul.studySpringbootMybatisRedis.controller;

import com.unimelb.saul.studySpringbootMybatisRedis.domain.JsonData;
import com.unimelb.saul.studySpringbootMybatisRedis.domain.User;
import com.unimelb.saul.studySpringbootMybatisRedis.enumClass.StateType;
import com.unimelb.saul.studySpringbootMybatisRedis.service.UserService;
import com.unimelb.saul.studySpringbootMybatisRedis.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @Autowired
    private RedisUtils redisUtils;


    @GetMapping("/login")
    public JsonData checkLogin(String email, String password) {
        return userService.checkLogin(email, password);
    }

    @GetMapping("/register")
    public JsonData register(String email, String password, String username) {
        return userService.register(email, password, username);
    }

    @GetMapping("/update")
    public JsonData updatePersonalInfo(String email, String username) {
        User user = userService.updatePersonalInfo(email, username);
        if (!username.equals(user.getUsername()))
            return JsonData.buildError(StateType.PROCESSING_EXCEPTION.getCode(), StateType.PROCESSING_EXCEPTION.value());
        return JsonData.buildSuccess(user, "过期时间" + redisUtils.getExpire(email));
    }

    @GetMapping("/findUser")
    public JsonData findUserByEmail(String email){
        User user = userService.findByEmail(email);
        if (user == null){
            return JsonData.buildError(StateType.BAD_REQUEST.getCode(), "the user is not exits");
        }
        return JsonData.buildSuccess(user, "过期时间" + redisUtils.getExpire(email));
    }

    @GetMapping("/delete")
    public JsonData deleteUser(String email){
        User user = userService.DeleteUser(email);
        if (user != null)
            return JsonData.buildSuccess(user, "过期时间" + redisUtils.getExpire(email));
        return JsonData.buildError(StateType.PROCESSING_EXCEPTION.getCode(), StateType.PROCESSING_EXCEPTION.value());

    }

    @GetMapping("/findAll")
    public JsonData findAllUser(){
        List<User> allUser = userService.findAllUser();
        if(allUser != null)
            return JsonData.buildSuccess(allUser);
        return JsonData.buildError(StateType.PROCESSING_EXCEPTION.getCode(), StateType.PROCESSING_EXCEPTION.value());
    }
}
