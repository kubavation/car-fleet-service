package com.durys.jakub.carfleet.events;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class EventsConfiguration {

    @Bean
    Events events(RabbitTemplate rabbitTemplate) {
        return new RabbitmqEventPublisher(rabbitTemplate);
    }
}

