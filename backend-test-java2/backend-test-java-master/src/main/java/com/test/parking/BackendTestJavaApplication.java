package com.test.parking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BackendTestJavaApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendTestJavaApplication.class, args);
	}

}
