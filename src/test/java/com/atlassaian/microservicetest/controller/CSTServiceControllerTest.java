package com.atlassaian.microservicetest.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.atlassaian.microservicetest.AbstractTest;
import com.atlassaian.microservicetest.bl.CSTService;
import com.atlassaian.microservicetest.bl.NotificationService;

@WebMvcTest(CSTServiceController.class)
public class CSTServiceControllerTest extends AbstractTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private WebApplicationContext webApplicationContext;
    
    @MockBean
    private CSTService cSTService;

    @MockBean
    private NotificationService notificationService; 
    
    @MockBean
    private QueueMessagingTemplate queueMessagingTemplate;
    
    @MockBean
    private  AmazonSQSAsync amazonSQSAsync; 
    
    @MockBean
    private RestTemplate restTemplate;
    
    @Test
    public void getStoryPointSum() throws Exception {
       // arrange
       final int expectedSum = 5;	
       final String uri = "/api/issue/sum?query=xyz&name=testsearchName";
       
       Mockito.when(cSTService.calculateSum("xyz")).thenReturn(expectedSum);
       Mockito.doNothing().when(notificationService).publishSumResult("testsearchName", expectedSum);
       
       
      //act
       MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
          .accept(MediaType.APPLICATION_JSON)).andReturn();
       
       
       int status = mvcResult.getResponse().getStatus();
       final String content = mvcResult.getResponse().getContentAsString();
       
       
       //assert
       
       assertEquals(200, status);
       assertThat(content, is(equalTo("result is publish successfully and sum is 5")));
       
       Mockito.verify(cSTService).calculateSum("xyz");
       Mockito.verify(notificationService).publishSumResult("testsearchName", expectedSum);;

       
    }
}
