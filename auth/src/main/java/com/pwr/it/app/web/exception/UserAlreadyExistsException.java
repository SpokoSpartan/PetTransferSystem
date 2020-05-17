package com.pwr.it.app.web.exception;

public class UserAlreadyExistsException extends Exception {

    private static final String EXCEPTION_MSG = "Sorry, but user with that login " +
            "exists in the system. Please choose different name";

    public UserAlreadyExistsException() {
        super(EXCEPTION_MSG);
    }

}
