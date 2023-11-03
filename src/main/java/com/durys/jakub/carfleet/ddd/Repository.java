package com.durys.jakub.carfleet.ddd;

public interface Repository<T> {
    T save(T t);
}
