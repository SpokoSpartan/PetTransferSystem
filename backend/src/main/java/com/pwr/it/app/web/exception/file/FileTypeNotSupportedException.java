package com.pwr.it.app.web.exception.file;

public class FileTypeNotSupportedException extends FileValidationException {

    private static final String EXCEPTION_MSG = "Sorry, this file type is not supported. Choose another file";

    public FileTypeNotSupportedException() {
        super(EXCEPTION_MSG);
    }

}
