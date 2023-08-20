package com.gencode.issuetool;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class PrototypeApp {
	@Value( "${cors_url}" )
    private String corsUrl;

	public static void main(String[] args) {
		SpringApplication.run(PrototypeApp.class, args);
	}
}
