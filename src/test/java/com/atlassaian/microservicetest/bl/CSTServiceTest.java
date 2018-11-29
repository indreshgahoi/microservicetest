package com.atlassaian.microservicetest.bl;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.atlassaian.microservicetest.AbstractTest;
import com.atlassaian.microservicetest.bo.IssueStoryPoint;
import com.atlassaian.microservicetest.soa.JiraSAO;
import com.atlassaian.microservicetest.soa.request.JiraIssueSearchRequest;

@RunWith(MockitoJUnitRunner.class)
public class CSTServiceTest extends AbstractTest {

    @InjectMocks
    private CSTService cSTService;

    @Mock
    private JiraSAO jiraSAO;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private static final String TEST_QUERY = "assigne=joe";

    @Test
    public void calculateSum_emptySearchResponse() {
	// arrange
	final int expectedSum = 0;
	final JiraIssueSearchRequest searchRequest = JiraIssueSearchRequest.builder().searchQuery(TEST_QUERY).build();

	Mockito.when(jiraSAO.fetchIssueList(searchRequest)).thenReturn(new ArrayList<>());

	// act
	int actualSum = cSTService.calculateSum(TEST_QUERY);

	// assert
	assertThat(actualSum, is(equalTo(expectedSum)));
	Mockito.verify(jiraSAO).fetchIssueList(searchRequest);
    }

    @Test
    public void calculateSum_nonEmptySearchResponse() {
	// arrange
	final int expectedSum = 8;
	final JiraIssueSearchRequest searchRequest = JiraIssueSearchRequest.builder().searchQuery(TEST_QUERY).build();

	final List<IssueStoryPoint> searchResults = new ArrayList<>();
	final IssueStoryPoint issue1 = IssueStoryPoint.builder().issueKey("i1").point(3).build();
	final IssueStoryPoint issue2 = IssueStoryPoint.builder().issueKey("i2").point(5).build();

	searchResults.add(issue1);
	searchResults.add(issue2);

	Mockito.when(jiraSAO.fetchIssueList(searchRequest)).thenReturn(searchResults);

	// act
	int actualSum = cSTService.calculateSum(TEST_QUERY);

	// assert
	assertThat(actualSum, is(equalTo(expectedSum)));
	Mockito.verify(jiraSAO).fetchIssueList(searchRequest);
    }

    @Test
    public void calculateSum_emptyQuery() {
	expectedException.expect(IllegalArgumentException.class);
	cSTService.calculateSum("");
    }

    @Test
    public void calculateSum_NotemptyQuery() {
	expectedException.expect(IllegalArgumentException.class);
	cSTService.calculateSum(null);
    }

}
