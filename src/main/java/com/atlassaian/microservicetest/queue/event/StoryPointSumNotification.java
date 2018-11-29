package com.atlassaian.microservicetest.queue.event;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
@EqualsAndHashCode
public class StoryPointSumNotification implements Notification {
    private final String name;
    private final int totalPoints;
}
