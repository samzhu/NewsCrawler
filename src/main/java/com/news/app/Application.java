package com.news.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@SpringBootApplication
@EnableElasticsearchRepositories(basePackages = {"com.news.repository"})
@ComponentScan(basePackages="com.news")
public class Application{
	

	public static void main(String[] args) throws Exception {
		SpringApplication.run(Application.class, "--debug");
	}
	
	
}
