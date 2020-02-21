package com.unimelb.saul.studySpringbootMybatisRedis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.unimelb.saul.studySpringbootMybatisRedis.mapper")
public class StudySpringbootMybatisRedisApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudySpringbootMybatisRedisApplication.class, args);
	}

}
