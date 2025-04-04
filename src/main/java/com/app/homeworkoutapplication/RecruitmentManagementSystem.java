package com.app.homeworkoutapplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class RecruitmentManagementSystem {

	private final Environment env;

	public RecruitmentManagementSystem(Environment env) {
		this.env = env;
	}

	public static void main(String[] args) {
		SpringApplication.run(RecruitmentManagementSystem.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void afterStartup() {
		String accessUrl = "http://localhost:" + env.getProperty("server.port", "8080");
		String swaggerUrl = accessUrl + "/swagger-ui/index.html";

		System.out.println("Application is running! Access it at: " + accessUrl);
		System.out.println("Swagger UI available at: " + swaggerUrl);
	}
}
