package com.durys.jakub.carfleet.ddd;

import com.durys.jakub.carfleet.events.DomainEvent;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Transient;
import jakarta.persistence.Version;

import java.util.Set;

@MappedSuperclass
public abstract class BaseAggregateRoot {

    @Transient
    protected Set<DomainEvent> events;

    @Version
    private Long version;

    public void append(DomainEvent event) {
        events.add(event);
    }

    public abstract AggregateId aggregateId();
}
