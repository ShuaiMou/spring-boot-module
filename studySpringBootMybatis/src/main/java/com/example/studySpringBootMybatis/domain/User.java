package com.example.studySpringBootMybatis.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class User {

	private int id;
	
	private String name;
	
	private String phone;
	
	private int age;
	
	private Date createTime;

}
