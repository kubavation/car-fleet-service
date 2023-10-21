package com.durys.jakub.carfleet.events;

public interface Events {
     <T> void publish(T event);
}
