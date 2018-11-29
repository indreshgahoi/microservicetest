package com.atlassaian.microservicetest.controller;

import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.atlassaian.microservicetest.bl.CSTService;
import com.atlassaian.microservicetest.bl.NotificationService;
import com.atlassaian.microservicetest.soa.response.JiraSearchResponse;
import com.atlassaian.microservicetest.soa.response.pojo.JiraIssue;
import com.atlassaian.microservicetest.soa.response.pojo.JiraIssueFields;
import com.google.common.base.Preconditions;

import lombok.extern.log4j.Log4j2;

/*
 * This is main Controller to find the issues and their storie's points and
 * and publish the request  
 */
@Log4j2
@RestController
public class CSTServiceController {

    @Autowired
    private CSTService cSTService;

    @Autowired
    private NotificationService notificationService;
    
    /**
     * 
     * @param query - query to get issues
     * @param descriptiveName
     * @return return  message with with http status
     */

    @GetMapping(value = "/api/issue/sum")
    public @ResponseBody ResponseEntity<String> getStoryPointSum(@RequestParam("query") String query,
	    @RequestParam("name") String descriptiveName) {

	Preconditions.checkArgument(StringUtils.isNotBlank(query));
	Preconditions.checkArgument(StringUtils.isNotBlank(descriptiveName));

	log.info("request received: query {}, descriptive name: {}", query, descriptiveName);
	int sum = cSTService.calculateSum(query);
	
	log.info("sum calucated: sum :{}", sum);
	
	notificationService.publishSumResult(descriptiveName, sum);
	
	log.info("Notify result successfully");
	return new ResponseEntity<String>("result is publish successfully and sum is " + sum, HttpStatus.OK);
    }
    
    
    
    @GetMapping(value = "/rest/api/2/search")
    public @ResponseBody ResponseEntity<JiraSearchResponse> getTempraryResponse(@RequestParam("q") String query
	    ) {
	 JiraSearchResponse response = new JiraSearchResponse();
	 response.setSearchResults(new ArrayList<>());
	 
         JiraIssue i1 = new JiraIssue();
         i1.setIssueKey("key1");
         JiraIssueFields fields = new JiraIssueFields();
         fields.setStoryPoints(1);
	 i1.setFields(fields );
	 
	 response.getSearchResults().add(i1);
	 
	
	
	return new ResponseEntity<JiraSearchResponse>(response, HttpStatus.OK);
    }
    
    

}
