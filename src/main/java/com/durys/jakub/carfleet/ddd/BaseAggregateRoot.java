package com.durys.jakub.carfleet.ddd;

import com.durys.jakub.carfleet.events.DomainEvent;

import java.util.Set;

public class BaseAggregateRoot {
    protected Set<DomainEvent> events;
}
