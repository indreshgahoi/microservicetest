package com.atlassaian.microservicetest.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.amazonaws.services.sqs.AmazonSQSAsync;

@Configuration
public class QueueMessagingTemplateConfig {
    
    @Autowired
    AmazonSQSAsync amazonSQSAsync;
    
    @Bean
    public QueueMessagingTemplate getQueueMessagingTemplate() {
	return new QueueMessagingTemplate(amazonSQSAsync);
    }
    
    @Bean 
    public RestTemplate getRestTemplate(){
	return new RestTemplate();
	
    }
    

}
