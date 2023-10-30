package com.durys.jakub.carfleet.events;

import org.springframework.context.ApplicationEventPublisher;

class SpringEventPublisher implements Events {

    private final ApplicationEventPublisher applicationEventPublisher;

    public SpringEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public <T> void publish(T event) {
        applicationEventPublisher.publishEvent(event);
    }
}
