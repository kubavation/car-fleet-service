package com.durys.jakub.carfleet.requests;

public interface Flowable<T> {
    void setState(String state);
    String state();
    T content();
    void setContent(T content);
}
