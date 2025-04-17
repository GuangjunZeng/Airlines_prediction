package com.newsportal;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.newsportal.Mapper")
public class NewsPortalApplication {

	public static void main(String[] args) {
		SpringApplication.run(NewsPortalApplication.class, args);
	}

}
