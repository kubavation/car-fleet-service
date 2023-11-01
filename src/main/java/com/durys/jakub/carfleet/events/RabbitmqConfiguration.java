package com.durys.jakub.carfleet.events;

import lombok.RequiredArgsConstructor;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
class RabbitmqConfiguration {

    private final CachingConnectionFactory cachingConnectionFactory;

    @Bean
    Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    RabbitTemplate rabbitTemplate(Jackson2JsonMessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(cachingConnectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }

    @Bean
    Declarables configuration(@Value("${queue.driver-request-acceptation}") String driverTransferQueue,
                              @Value("${exchange.driver-request-acceptation}") String driverTransferExchange) {

        Queue driverTransferRequestQueue = new Queue(driverTransferQueue, false);
        TopicExchange driverTransferRequestExchange = new TopicExchange(driverTransferExchange);

        return new Declarables(
                driverTransferRequestQueue,
                driverTransferRequestExchange,
                BindingBuilder.bind(driverTransferRequestQueue)
                        .to(driverTransferRequestExchange)
                        .with("acceptation"));

    }
}
