package com.atlassaian.microservicetest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.aws.autoconfigure.context.ContextRegionProviderAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.atlassaian.microservicetest.*")
@SpringBootApplication(exclude = ContextRegionProviderAutoConfiguration.class)
public class MicroservicetestApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroservicetestApplication.class, args);
	}
}
