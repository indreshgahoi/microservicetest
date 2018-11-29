package com.atlassaian.microservicetest.queue.sqs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Repository;

import com.atlassaian.microservicetest.queue.NotificationPublisher;
import com.atlassaian.microservicetest.queue.event.Notification;
import com.google.common.base.Preconditions;

import lombok.extern.log4j.Log4j2;

/**
 * This class is responsible to send message to queue
 * @author indreshg
 *
 */
@Log4j2
@Repository
public class SQSPublisher implements NotificationPublisher {

    
    private QueueMessagingTemplate queueMessagingTemplate;
   
    private String qName ;
    
    @Autowired
    public SQSPublisher(final QueueMessagingTemplate queueMessagingTemplate,  @Value("${sqs.name}") final String name) {
	this.queueMessagingTemplate = queueMessagingTemplate;
	this.qName = name;
    }
    
    
   
    
    @Override
    public void publish(final Notification notification) {
	Preconditions.checkNotNull(notification, "notification can not be null");
	
	log.info("Sending event to the queue: {} : event: {}", qName, notification);
	final Message<Notification> paylod = MessageBuilder.withPayload(notification).build();
	this.queueMessagingTemplate.convertAndSend(this.qName, paylod);
	log.info("successfully sent the event: queue{} : paylod: {}", qName, paylod);

    }

}
