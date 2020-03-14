package com.sample.api;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class SampleApiApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder()
			.sources(SampleApiApplication.class)
			.run(args);
	}
}