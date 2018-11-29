package com.atlassaian.microservicetest.bo;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
@EqualsAndHashCode
public class IssueStoryPoint {
	private String issueKey;
	private int point;
}
