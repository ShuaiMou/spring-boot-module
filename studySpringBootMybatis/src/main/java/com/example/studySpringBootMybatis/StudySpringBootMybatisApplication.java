package com.example.studySpringBootMybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.studySpringBootMybatis.mapper")
public class StudySpringBootMybatisApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudySpringBootMybatisApplication.class, args);
	}

}
