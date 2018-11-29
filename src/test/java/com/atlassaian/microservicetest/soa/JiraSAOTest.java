package com.atlassaian.microservicetest.soa;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.equalTo;
import org.mockito.Matchers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.atlassaian.microservicetest.AbstractTest;
import com.atlassaian.microservicetest.bo.IssueStoryPoint;
import com.atlassaian.microservicetest.soa.request.JiraIssueSearchRequest;
import com.atlassaian.microservicetest.soa.response.JiraSearchResponse;
import com.atlassaian.microservicetest.soa.response.pojo.JiraIssue;
import com.atlassaian.microservicetest.soa.response.pojo.JiraIssueFields;

@RunWith(MockitoJUnitRunner.class)
public class JiraSAOTest extends AbstractTest {

    private JiraSAO jiraSAO;

    private RestTemplate restTemplate;

    @Before
    public void setup() {
	restTemplate = Mockito.mock(RestTemplate.class);
	jiraSAO = new JiraSAO(restTemplate, "http://localhost:8080");
    }

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void fetchIssueList_nullResponseFromJira() {

	// arrange
	final String searchQuesry = "assign=joe";
	final JiraIssueSearchRequest searchRequest = JiraIssueSearchRequest.builder().searchQuery(searchQuesry).build();
	final Map<String, String> uriVariables = new HashMap<>();
	uriVariables.put("q", searchQuesry);

	Mockito.when(restTemplate.getForEntity(Matchers.anyString(), Matchers.<Class<JiraSearchResponse>>any(),
		Mockito.any(Map.class))).thenReturn(new ResponseEntity(null, HttpStatus.BAD_REQUEST));

	expectedException.expect(RuntimeException.class);
	expectedException.expectMessage("get null response from the jira");
	expectedException.expectMessage(HttpStatus.BAD_REQUEST.getReasonPhrase());

	// act
	jiraSAO.fetchIssueList(searchRequest);

    }

    @Test
    public void fetchIssueList_success() {
	// arrange
	final String searchQuesry = "assign=joe";
	final JiraIssueSearchRequest searchRequest = JiraIssueSearchRequest.builder().searchQuery(searchQuesry).build();
	final Map<String, String> uriVariables = new HashMap<>();
	uriVariables.put("q", searchQuesry);

	JiraIssue i1 = new JiraIssue();
	i1.setIssueKey("key1");
	JiraIssueFields fields = new JiraIssueFields();
	fields.setStoryPoints(1);
	i1.setFields(fields);
	final JiraSearchResponse body = new JiraSearchResponse();
	body.setSearchResults(Arrays.asList(i1));

	final List<IssueStoryPoint> expectedOutput = Arrays
		.asList(IssueStoryPoint.builder().issueKey("key1").point(1).build());

	ResponseEntity<JiraSearchResponse> response = new ResponseEntity<JiraSearchResponse>(body, HttpStatus.OK);

	Mockito.when(restTemplate.getForEntity(Matchers.anyString(), Matchers.<Class<JiraSearchResponse>>any(),
		Mockito.any(Map.class))).thenReturn(response);

	// act
	final List<IssueStoryPoint> actualOutput = jiraSAO.fetchIssueList(searchRequest);

	// assert
	Mockito.verify(restTemplate, Mockito.times(1)).getForEntity(Matchers.anyString(),
		Matchers.<Class<JiraSearchResponse>>any(), Mockito.any(Map.class));
	assertThat(actualOutput, is(equalTo(expectedOutput)));

    }

    @Test
    public void fetchIssueList_withNullRequest() {
	expectedException.expect(NullPointerException.class);
	expectedException.expectMessage("searchrequest can not be null");

	jiraSAO.fetchIssueList(null);

    }

}
