package com.unimelb.saul.studySpringbootMybatisRedis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@MapperScan("com.unimelb.saul.studySpringbootMybatisRedis.mapper")
@EnableCaching
public class StudySpringbootMybatisRedisApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudySpringbootMybatisRedisApplication.class, args);
	}

}
