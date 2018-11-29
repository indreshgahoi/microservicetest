package com.atlassaian.microservicetest;

import java.io.IOException;

import org.junit.runner.RunWith;
import org.springframework.boot.json.JsonParseException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebAppConfiguration
public abstract class AbstractTest {

  

    static {
	System.setProperty("JIRA_BASE_URL", "http://localhost:8080");
	System.setProperty("QUEUE_URL", "queueurl");

    }
    
    protected String mapToJson(Object obj) throws JsonProcessingException {
	ObjectMapper objectMapper = new ObjectMapper();
	return objectMapper.writeValueAsString(obj);
    }

    protected <T> T mapFromJson(String json, Class<T> clazz)
	    throws JsonParseException, JsonMappingException, IOException {

	ObjectMapper objectMapper = new ObjectMapper();
	return objectMapper.readValue(json, clazz);
    }
}