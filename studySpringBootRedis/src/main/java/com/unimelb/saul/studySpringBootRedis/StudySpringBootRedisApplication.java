package com.unimelb.saul.studySpringBootRedis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class StudySpringBootRedisApplication {
	public static void main(String[] args) {
		SpringApplication.run(StudySpringBootRedisApplication.class, args);
	}

}
