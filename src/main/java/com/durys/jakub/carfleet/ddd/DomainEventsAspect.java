package com.durys.jakub.carfleet.ddd;

import com.durys.jakub.carfleet.events.Events;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
class DomainEventsAspect {

    private final Events events;

    @AfterReturning(value = "execution(* com.durys.jakub.carfleet.ddd.Repository.save(..))", returning = "root")
    public void emitDomainEvents(BaseAggregateRoot root) {

        log.info("publishing domain events");

        root.events
                .stream()
                .forEach(events::publish);
    }

}

