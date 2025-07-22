package com.example.taskmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TaskManagerApplication {

	public static void main(String[] args) {
		System.out.println("Hello");
		SpringApplication.run(TaskManagerApplication.class, args);
	}

}
