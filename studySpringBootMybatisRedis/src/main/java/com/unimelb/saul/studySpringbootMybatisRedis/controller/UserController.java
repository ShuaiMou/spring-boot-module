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

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.LinkedList;
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
    public JsonData checkLogin(HttpServletRequest request) {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        JsonData jsonData = userService.checkLogin(email, password);
        if (jsonData.getData() != null)
            request.getSession().setAttribute(email, email);
        return jsonData;
    }

    @GetMapping("/getSession")
    public JsonData getSession(HttpServletRequest request){
        Enumeration<String> attributeNames = request.getSession().getAttributeNames();
        List<String> list = new LinkedList<>();
        while (attributeNames.hasMoreElements()){
            list.add(attributeNames.nextElement());
        }
        return JsonData.buildSuccess(list);
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
