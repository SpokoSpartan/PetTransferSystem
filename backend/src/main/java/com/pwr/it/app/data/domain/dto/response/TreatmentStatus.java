package com.pwr.it.app.data.domain.dto.response;

public enum TreatmentStatus {

    CANCELED("canceled"),
    HISTORY("history"),
    IN_PROGRESS("progress"),
    PLANNED("planned"),
    UNKNOWN("unknown");

    String statusName;

    TreatmentStatus(String statusName) {
        this.statusName = statusName;
    }

    @Override
    public String toString() {
        return statusName;
    }

}
