package com.example.studySpringBootMybatis.service.impl;

import com.example.studySpringBootMybatis.domain.User;
import com.example.studySpringBootMybatis.mapper.UserMapper;
import com.example.studySpringBootMybatis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

	 @Autowired
	 private UserMapper userMapper;
	 
	@Override
	public int add(User user) {
		userMapper.insert(user);
		return user.getId();
	}

	@Override
    @Transactional(propagation = Propagation.REQUIRED)
	public int addAccount(User user)  {
	    user.setName("测试事务，加入事务");
		userMapper.insert(user);
		int i = 10/0;
		return 0;
	}
}
