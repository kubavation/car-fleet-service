package com.durys.jakub.carfleet.state;

public interface Flowable<T> {
    void setState(String state);
    String state();
    T content();
    void setContent(T content);
}
