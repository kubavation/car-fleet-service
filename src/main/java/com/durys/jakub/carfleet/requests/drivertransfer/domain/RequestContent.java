package com.durys.jakub.carfleet.requests.drivertransfer.domain;

import com.durys.jakub.carfleet.plannedevent.PlannedEvent;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
class RequestContent {

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "identifier", column = @Column(name = "EVENT_IDENTIFIER")),
            @AttributeOverride(name = "from", column = @Column(name = "EVENT_FROM")),
            @AttributeOverride(name = "to", column = @Column(name = "EVENT_TO")),
            @AttributeOverride(name = "place", column = @Column(name = "EVENT_PLACE"))
    })
    private final PlannedEvent event;

    private final String departure;

    RequestContent(PlannedEvent event, String departure) {
        this.event = event;
        this.departure = departure;
    }

    PlannedEvent plannedEvent() {
        return event;
    }

    String departure() {
        return departure;
    }
}



