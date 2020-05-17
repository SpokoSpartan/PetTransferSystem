package com.pwr.it.app.web.exception;

public class UserNotFoundException extends Exception {

    private static final String EXCEPTION_MSG = "Sorry, but we cannot find this user. Refresh this page and try again.";

    public UserNotFoundException() {
        super(EXCEPTION_MSG);
    }

}
