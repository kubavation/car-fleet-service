package com.durys.jakub.carfleet.drivers.domain;

import com.durys.jakub.carfleet.common.Status;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
@Table
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class Driver {

    @EmbeddedId
    @AttributeOverrides({
        @AttributeOverride(name = "value", column = @Column(name = "ID"))
    })

    private final DriverId driverId;
    private String firstName;
    private String lastName;
    private Status status;

    @OneToMany
    @JoinColumn(name = "driver_id")
    private Set<Absence> absences;

    public Driver(DriverId driverId, String firstName, String lastName, Status status) {
        this.driverId = driverId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.status = status;
        this.absences = new HashSet<>();
    }

    public Driver(DriverId driverId, String firstName, String lastName) {
        this(driverId, firstName, lastName, Status.ACTIVE);
    }

    public void archive() {
        this.status = Status.ARCHIVED;
    }

    public void activate() {
        this.status = Status.ACTIVE;
    }

    public void updatePersonalInformation(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void markAsInactiveBetween(LocalDate from, LocalDate to) {
        Set<Absence> absences = Stream.iterate(from, date -> date.plusDays(1))
                .limit(ChronoUnit.DAYS.between(from, to) + 1)
                .map(Absence::new)
                .collect(Collectors.toSet());
        this.absences.addAll(absences);
    }

    public boolean activeBetween(LocalDate from, LocalDate to) {
        return status != Status.ARCHIVED &&
                Stream.iterate(from, date -> date.plusDays(1))
                    .limit(ChronoUnit.DAYS.between(from, to) + 1)
                    .map(Absence::new)
                    .noneMatch(date -> absences.contains(date));
    }

}
