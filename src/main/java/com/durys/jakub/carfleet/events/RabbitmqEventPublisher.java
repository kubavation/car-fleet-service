package com.durys.jakub.carfleet.events;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

@RequiredArgsConstructor
class RabbitmqEventPublisher implements Events {

    private final RabbitTemplate rabbitTemplate;

    @Override
    public <T> void publish(T event) {
        rabbitTemplate.convertAndSend("todo-queue", event);
    }
}
