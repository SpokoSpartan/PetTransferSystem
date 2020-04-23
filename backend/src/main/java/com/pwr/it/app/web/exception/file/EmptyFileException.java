package com.pwr.it.app.web.exception.file;

public class EmptyFileException extends FileValidationException {

    private static final String EXCEPTION_MSG = "Empty file sent. Select an existing file";

    public EmptyFileException() {
        super(EXCEPTION_MSG);
    }

}
