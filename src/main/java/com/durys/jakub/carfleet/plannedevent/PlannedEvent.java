package com.durys.jakub.carfleet.plannedevent;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class PlannedEvent {

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "EVENT_ID"))
    private final PlannedEventId eventId;
    private final String identifier;
    private final LocalDateTime from;
    private final LocalDateTime to;
    private final String place;

    public PlannedEvent(PlannedEventId eventId, String identifier, LocalDateTime from, LocalDateTime to, String place) {
        this.eventId = eventId;
        this.identifier = identifier;
        this.from = from;
        this.to = to;
        this.place = place;
    }

    public PlannedEventId eventId() {
        return eventId;
    }

    public String identifier() {
        return identifier;
    }

    public LocalDateTime from() {
        return from;
    }

    public LocalDateTime to() {
        return to;
    }

    public String place() {
        return place;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (PlannedEvent) obj;
        return Objects.equals(this.eventId, that.eventId) &&
                Objects.equals(this.identifier, that.identifier) &&
                Objects.equals(this.from, that.from) &&
                Objects.equals(this.to, that.to) &&
                Objects.equals(this.place, that.place);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId, identifier, from, to, place);
    }

}
