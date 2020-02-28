package com.unimelb.saul.studySpringBootSwagger.controller;

import com.unimelb.saul.studySpringBootSwagger.domain.JsonData;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/***
 * @Author Saul
 * @Description  TODO: 测试Swagger
 * @Date 3:17 PM 27/2/20
 */
@RestController
@Api(tags = "用户管理")
@RequestMapping("/user")
public class UserController {

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
    @ApiOperation(value = "删除用户",notes = "根据用户邮箱删除用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "email", value = "用户唯一标识符-邮箱",required = true, paramType = "delete", dataType = "String")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = JsonData.class),
            @ApiResponse(code = 404, message = "not found", response = JsonData.class),
    })
    public JsonData deleteUser(String email){
        return JsonData.buildSuccess();

    }

    @GetMapping("/findAll")
    public JsonData findAllUser(){
        return JsonData.buildSuccess();
    }
}
