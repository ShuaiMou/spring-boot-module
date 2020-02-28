package com.unimelb.saul.studySpringBootSwagger.controller;

import com.unimelb.saul.studySpringBootSwagger.domain.JsonData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/***
 * @Author Saul
 * @Description  TODO: 测试Swagger
 * @Date 8:10 下午 28/2/20
 */
@RestController
@Api(tags = "文件管理")
@RequestMapping("/file")
public class FileController {

    @PostMapping("/login")
    @ApiOperation(value = "登录检查",consumes = "测试", notes = "测试用")
    public JsonData checkLogin(String username) {
        return JsonData.buildSuccess(username);
    }

    @GetMapping("/getSession")
    @ApiOperation(value = "获取session",notes = "获取session的测试")
    public JsonData getSession(HttpServletRequest request){
        return JsonData.buildSuccess();
    }

    @PostMapping("/register")
    @ApiOperation(value = "新增用户",notes = "新增注册")
    public JsonData register(String email, String password, String username) {
        return JsonData.buildSuccess();
    }

    @PutMapping("/update")
    public JsonData updatePersonalInfo(String email, String username) {
        return JsonData.buildSuccess();
    }

    @GetMapping("/findUser")
    public JsonData findUserByEmail(String email){
        return JsonData.buildSuccess();
    }

    @DeleteMapping("/delete")
    public JsonData deleteUser(String email){
        return JsonData.buildSuccess();

    }

    @GetMapping("/findAll")
    public JsonData findAllUser(){
        return JsonData.buildSuccess();
    }
}
