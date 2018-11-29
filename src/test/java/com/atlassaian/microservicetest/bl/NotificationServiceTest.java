package com.atlassaian.microservicetest.bl;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.atlassaian.microservicetest.AbstractTest;
import com.atlassaian.microservicetest.queue.NotificationPublisher;
import com.atlassaian.microservicetest.queue.event.Notification;
import com.atlassaian.microservicetest.queue.event.StoryPointSumNotification;

@RunWith(MockitoJUnitRunner.class)
public class NotificationServiceTest extends AbstractTest {

    @InjectMocks
    private NotificationService notificationService;

    @Mock
    private NotificationPublisher publisher;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void publishSumResult_success() {
	// arrange
	final String descriptiveName = "searchResultForBug";
	final int expectedSum = 20;
	final Notification notification = StoryPointSumNotification.builder().name(descriptiveName)
		.totalPoints(expectedSum).build();

	// act
	notificationService.publishSumResult(descriptiveName, expectedSum);

	// verify
	Mockito.verify(publisher).publish(notification);

    }

    @Test
    public void publishSumResult_NullDescrptiveName() {

	expectedException.expect(IllegalArgumentException.class);
	expectedException.expectMessage("can't be null for empty");

	notificationService.publishSumResult(null, 20);

    }

    @Test
    public void publishSumResult_EmptyDescrptiveName() {
	expectedException.expect(IllegalArgumentException.class);
	expectedException.expectMessage("can't be null for empty");

	notificationService.publishSumResult("", 20);
    }

    @Test
    public void publishSumResult_NegativeSum() {
	expectedException.expect(IllegalArgumentException.class);
	expectedException.expectMessage("can not be negative");

	notificationService.publishSumResult("somename", -20);
    }

}
