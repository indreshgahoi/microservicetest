package com.atlassaian.microservicetest.bl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.atlassaian.microservicetest.queue.NotificationPublisher;
import com.atlassaian.microservicetest.queue.event.Notification;
import com.atlassaian.microservicetest.queue.event.StoryPointSumNotification;
import com.google.common.base.Preconditions;

import lombok.extern.log4j.Log4j2;

/**
 * This class has responsility to publish the Notification to queue
 * @author indreshg
 *
 */
@Log4j2
@Component
public class NotificationService {
    
    @Autowired
    private NotificationPublisher notificationPublisher;
    
    /**
     * 
     * @param descriptiveName - descriptive name
     * @param sum - int totalStorypoint to submit  
     */

    public void publishSumResult(final String descriptiveName, final int sum) {
	Preconditions.checkArgument(StringUtils.isNotBlank(descriptiveName), "descriptiveName can't be null for empty");
	Preconditions.checkArgument(sum >= 0, "sum can not be negative");

	final Notification notification = StoryPointSumNotification.builder().name(descriptiveName).totalPoints(sum)
		.build();
	log.info("publishing the event to queue event{}", notification);
	notificationPublisher.publish(notification);
	log.info("successfully triggered publish the event{}", notification);
 
    }

}
