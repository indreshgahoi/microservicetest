package com.atlassaian.microservicetest.queue.sqs;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;

import com.atlassaian.microservicetest.AbstractTest;
import com.atlassaian.microservicetest.queue.event.Notification;
import com.atlassaian.microservicetest.queue.event.StoryPointSumNotification;

@RunWith(MockitoJUnitRunner.class)
public class SQSPublisherTest extends AbstractTest {

    
    private SQSPublisher publisher;
    
    
    private QueueMessagingTemplate queueMessagingTemplate;
    
    @Before
    public void setUp() {
	queueMessagingTemplate = Mockito.mock(QueueMessagingTemplate.class);
	publisher = new SQSPublisher(queueMessagingTemplate, "queueurl");
    }
    
   @Test
    public void publish_success() {
        final Notification notification = StoryPointSumNotification.builder().name("abc").totalPoints(5).build();

	publisher.publish(notification);
	Mockito.verify(queueMessagingTemplate,Mockito.times(1)).convertAndSend(Mockito.anyString(), Mockito.any(Object.class));

    }

    

}
