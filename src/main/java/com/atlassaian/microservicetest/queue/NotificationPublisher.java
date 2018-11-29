package com.atlassaian.microservicetest.queue;

import com.atlassaian.microservicetest.queue.event.Notification;

/*
 * This is abstraction to the publisher queue
 * sqs, kafka publisher can implement to publish
 */
public interface NotificationPublisher {
    
    /**
     * this method needs to be implemented by the specific published
     * 
     * @param notification - {@link Notification}
     */
    void publish(Notification notification);

}
