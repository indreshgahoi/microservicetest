package com.atlassaian.microservicetest.soa.request;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
@EqualsAndHashCode
public class JiraIssueSearchRequest {
    private final String searchQuery;
}
