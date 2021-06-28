package com.leanix.app.toDoApp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@Slf4j
@EnableMongoRepositories("com.leanix.app.toDoApp.services")
public class ToDoAppApplication {

	public static void main(String[] args) {
		String mongodb_url = System.getenv("MONGODB_URL");
		log.info(String.format("MONGODB_URL was set to: %s", mongodb_url));

		SpringApplication.run(ToDoAppApplication.class, args);
	}

}
