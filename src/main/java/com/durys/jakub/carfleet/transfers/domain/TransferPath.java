package com.durys.jakub.carfleet.transfers.domain;

import java.util.List;

class TransferPath {

    private final Place from;
    private final Place to;

    private final List<Place> places;

    //todo

    public TransferPath(Place from, Place to, List<Place> places) {
        this.from = from;
        this.to = to;
        this.places = places;
    }

    Place from() {
        return from;
    }

    Place to() {
        return to;
    }
}
