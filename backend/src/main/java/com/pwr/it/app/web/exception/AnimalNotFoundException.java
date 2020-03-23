package com.pwr.it.app.web.exception;

public class AnimalNotFoundException extends Exception {

    private static final String EXCEPTION_MSG = "Sorry, but we cannot find this animal. Refresh this page and try again.";

    public AnimalNotFoundException() {
        super(EXCEPTION_MSG);
    }

}
