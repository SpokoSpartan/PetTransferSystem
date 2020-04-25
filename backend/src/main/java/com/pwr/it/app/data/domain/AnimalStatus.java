package com.pwr.it.app.data.domain;

public enum AnimalStatus {
    NEW_IN_SHELTER ("new in shelter"),
    READY_FOR_ADOPTION ("ready for adoption"),
    ADOPTED ("adopted"),
    DIED("dead");

    String statusName;

    AnimalStatus(String statusName) {
        this.statusName = statusName;
    }

    @Override
    public String toString() {
        return statusName;
    }

}
