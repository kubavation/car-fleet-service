package com.durys.jakub.carfleet.requests.state;

import com.durys.jakub.carfleet.requests.Request;

public interface StateConfig {
    State recreate(Request request);
    State begin(Request request);
}
