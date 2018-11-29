package com.atlassaian.microservicetest.bl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.atlassaian.microservicetest.bo.IssueStoryPoint;
import com.atlassaian.microservicetest.soa.JiraSAO;
import com.atlassaian.microservicetest.soa.request.JiraIssueSearchRequest;
import com.google.common.base.Preconditions;

import lombok.extern.log4j.Log4j2;

/**
 * This class is responsible for business logic for CST Service
 * 
 * @author indreshg
 *
 */
@Log4j2
@Component
public class CSTService {
    
    @Autowired
    private JiraSAO jiraSAO;

    /**
     * 
     * @param query - search query to fetch issues
     * @return sum of all the story points
     */
    public int calculateSum(final String query) {
	Preconditions.checkArgument(StringUtils.isNotBlank(query), "query can't be null for empty");
	
	log.info("triggering the search query to jira");
	final JiraIssueSearchRequest searchRequest = JiraIssueSearchRequest.builder().searchQuery(query).build();
	
	List<IssueStoryPoint> issueStoryPoints = jiraSAO.fetchIssueList(searchRequest);
	
	log.info("successfully get the result from the jira {}", issueStoryPoints);


	return issueStoryPoints.parallelStream().mapToInt(issueStoryPoint -> issueStoryPoint.getPoint()).sum();
    }

}
