package com.atlassaian.microservicetest.soa.convertor;

import java.util.List;
import java.util.stream.Collectors;

import com.atlassaian.microservicetest.bo.IssueStoryPoint;
import com.atlassaian.microservicetest.soa.response.JiraSearchResponse;
import com.atlassaian.microservicetest.soa.response.pojo.JiraIssue;
import com.google.common.base.Preconditions;

import lombok.experimental.UtilityClass;

@UtilityClass
public class JiraResponseConvertor {

	public static List<IssueStoryPoint> convertSearchResultTo(final JiraSearchResponse response) {
		Preconditions.checkNotNull(response, "JiraSearchResponse can not be null");
		return response.getSearchResults()
				.stream()
				.map(JiraResponseConvertor::buildStoryPointFrom)
				.collect(Collectors.toList());
	}

	private IssueStoryPoint buildStoryPointFrom(final JiraIssue issue) {
		return IssueStoryPoint.builder()
				.issueKey(issue.getIssueKey())
				.point(issue.getFields() != null ? issue.getFields().getStoryPoints() : 0)
				.build();
	}

}
