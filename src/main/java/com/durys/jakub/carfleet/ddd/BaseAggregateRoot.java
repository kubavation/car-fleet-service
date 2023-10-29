package com.durys.jakub.carfleet.ddd;

import com.durys.jakub.carfleet.events.DomainEvent;
import jakarta.persistence.Version;

import java.util.Set;

public class BaseAggregateRoot {

    private Set<DomainEvent> events;

//    @Version
    private Integer version;
}
