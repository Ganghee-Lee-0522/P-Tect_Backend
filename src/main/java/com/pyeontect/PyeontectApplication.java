package com.pyeontect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PyeontectApplication {

	public static final String APPLICATION_LOCATIONS = "spring.config.location="
			+ "classpath:application.properties,"
			+ "src/main/resources/application-db.properties"
			+ "src/main/resources/application-jwt.properties";

	public static void main(String[] args) {
		SpringApplication.run(PyeontectApplication.class, args);
	}

}
