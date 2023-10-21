package com.durys.jakub.carfleet.events.spring;

import com.durys.jakub.carfleet.events.Events;
import org.springframework.context.ApplicationEventPublisher;

public class SpringEventPublisher implements Events {

    private final ApplicationEventPublisher applicationEventPublisher;

    public SpringEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public <T> void publish(T event) {
        applicationEventPublisher.publishEvent(event);
    }
}
