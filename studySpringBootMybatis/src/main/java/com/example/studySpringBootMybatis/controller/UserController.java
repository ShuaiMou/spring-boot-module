package com.example.studySpringBootMybatis.controller;


import com.example.studySpringBootMybatis.domain.JsonData;
import com.example.studySpringBootMybatis.domain.User;
import com.example.studySpringBootMybatis.mapper.UserMapper;
import com.example.studySpringBootMybatis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/user")
public class UserController {
	
	
	@Autowired
	private UserService userService;
	
	
	/**
	 * 功能描述: user 保存接口
	 * @return jsondata
	 */
	@GetMapping("add")
	public Object add(){
		
		User user = new User();
		user.setAge(11);
		user.setCreateTime(new Date());
		user.setName("saul");
		user.setPhone("10010000");
		int id = userService.add(user);
       return JsonData.buildSuccess(id);
	}

	@GetMapping("add_account")
	public Object addAccount(){
		User user = new User();
		user.setAge(91);
		user.setCreateTime(new Date());
		user.setName("测试事务");
		user.setPhone("999999");
		return userService.addAccount(user);
	}
	

	@Autowired
	private UserMapper userMapper;



	@GetMapping("findAll")
	public Object findAll(){

       return JsonData.buildSuccess(userMapper.getAll());
	}



	@GetMapping("findById")
	public Object findById(long id){
       return JsonData.buildSuccess(userMapper.findById(id));
	}


	@GetMapping("del_by_id")
	public Object delById(long id){
		userMapper.delete(id);
        return JsonData.buildSuccess();
	}

	@GetMapping("update")
	public Object update(String name,int id){
		User user = new User();
		user.setName(name);
		user.setId(id);
		userMapper.update(user);
	    return JsonData.buildSuccess();
	}


	
//	//测试事务
//	@GetMapping("transac")
//	public Object transac(){
//		int id = userService.addAccount();
//	    return JsonData.buildSuccess(id);
//	}
//	
//	
	
	
}
