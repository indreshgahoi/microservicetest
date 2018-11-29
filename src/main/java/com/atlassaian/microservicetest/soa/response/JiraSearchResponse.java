package com.atlassaian.microservicetest.soa.response;

import java.util.List;

import com.atlassaian.microservicetest.soa.response.pojo.JiraIssue;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@EqualsAndHashCode
public class JiraSearchResponse {
	List<JiraIssue> searchResults;
}
