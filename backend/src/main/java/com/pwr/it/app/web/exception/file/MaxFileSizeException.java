package com.pwr.it.app.web.exception.file;

public class MaxFileSizeException extends FileValidationException {

    private static final String EXCEPTION_MSG = "The size of this file exceeds the maximum size allowed. Use a smaller file";

    public MaxFileSizeException() {
        super(EXCEPTION_MSG);
    }

}
