package com.suriyaprakhash.gatling_java_sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

//@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
@SpringBootApplication
public class GatlingJavaSampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatlingJavaSampleApplication.class, args);
	}

}
