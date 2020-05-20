package com.pwr.it.app.web.exception;

public class TreatmentNotFoundException extends Exception {

    private static final String EXCEPTION_MSG = "Sorry, but we cannot find this treatment. Refresh this page and try again.";

    public TreatmentNotFoundException() {
        super(EXCEPTION_MSG);
    }

}
