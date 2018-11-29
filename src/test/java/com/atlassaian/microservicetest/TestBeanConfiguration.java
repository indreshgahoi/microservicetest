package com.atlassaian.microservicetest;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import com.atlassaian.microservicetest.bl.CSTService;

@Profile("test")
@Configuration
public class TestBeanConfiguration {

    @Bean
    @Primary
    public CSTService productService() {
	return Mockito.mock(CSTService.class);
    }

}
