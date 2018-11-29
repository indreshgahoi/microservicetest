package com.atlassaian.microservicetest.soa;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import com.atlassaian.microservicetest.bo.IssueStoryPoint;
import com.atlassaian.microservicetest.soa.convertor.JiraResponseConvertor;
import com.atlassaian.microservicetest.soa.request.JiraIssueSearchRequest;
import com.atlassaian.microservicetest.soa.response.JiraSearchResponse;
import com.google.common.base.Preconditions;

import lombok.extern.log4j.Log4j2;

/**
 * This represent the JIRA service Access Object Abstraction to the Jira Service
 * 
 * @author indreshg
 *
 */
@Log4j2
@Repository
public class JiraSAO {
    private static final String PATH = "/rest/api/2/search?q={q}";

    private RestTemplate restTemplate;

    private String baseUrl;

    @Autowired
    public JiraSAO(final RestTemplate restTemplate, @Value("${jira.baseurl}") final String baseUrl) {
	this.restTemplate = restTemplate;
	this.baseUrl = baseUrl;
    }

    /**
     * 
     * @param searchRequest - this search query to pull the issue from the JIRA
     *                      system
     * @return the list of {@link IssueStoryPoint}
     */
    public List<IssueStoryPoint> fetchIssueList(final JiraIssueSearchRequest searchRequest) {
	Preconditions.checkNotNull(searchRequest, "searchrequest can not be null");
	final Map<String, String> uriVariables = new HashMap<>();
	uriVariables.put("q", searchRequest.getSearchQuery());

	final String path = getPath();
	log.info("triggering the search request: {} path: {}", searchRequest, path);

	ResponseEntity<JiraSearchResponse> responseEntity = restTemplate.getForEntity(path, JiraSearchResponse.class,
		uriVariables);
	if (responseEntity.getBody() == null) {
	    throw new RuntimeException(
		    "get null response from the jira " + responseEntity.getStatusCode().getReasonPhrase());
	}
	final JiraSearchResponse response = responseEntity.getBody();
	log.info("jira search response: {}", response);

	return JiraResponseConvertor.convertSearchResultTo(response);
    }

    private String getPath() {
	return this.baseUrl + PATH;
    }

}
