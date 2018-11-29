package com.atlassaian.microservicetest.soa.convertor;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.equalTo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.atlassaian.microservicetest.bo.IssueStoryPoint;
import com.atlassaian.microservicetest.soa.response.JiraSearchResponse;
import com.atlassaian.microservicetest.soa.response.pojo.JiraIssue;
import com.atlassaian.microservicetest.soa.response.pojo.JiraIssueFields;

public class JiraResponseConvertorTest {

    @Test
    public void convertSearchResultTo_success() {
	// arrange
	final JiraSearchResponse response = new JiraSearchResponse();
	response.setSearchResults(new ArrayList<>());

	final JiraIssueFields fields = new JiraIssueFields();
	fields.setStoryPoints(1);

	final JiraIssue issue1 = new JiraIssue();
	issue1.setIssueKey("issue1");
	issue1.setFields(fields);

	response.getSearchResults().add(issue1);

	final IssueStoryPoint issueStoryPoint = IssueStoryPoint.builder().issueKey("issue1").point(1).build();

	// act
	final List<IssueStoryPoint> actulaOutput = JiraResponseConvertor.convertSearchResultTo(response);

	// assert
	assertThat(actulaOutput, is(equalTo(Arrays.asList(issueStoryPoint))));
    }

}
