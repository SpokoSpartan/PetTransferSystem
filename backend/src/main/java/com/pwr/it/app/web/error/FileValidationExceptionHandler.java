package com.pwr.it.app.web.error;


import com.pwr.it.app.web.exception.file.FileValidationException;
import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;

import javax.inject.Singleton;

@Produces
@Singleton
@Requires(classes = {FileValidationException.class, ExceptionHandler.class})
public class FileValidationExceptionHandler implements ExceptionHandler<FileValidationException, HttpResponse>  {


    @Override
    public HttpResponse handle(HttpRequest request, FileValidationException exception) {
        ApiError apiError = new ApiError("FILE_VALIDATION_ERROR", exception.getMessage());
        return HttpResponse.badRequest(apiError);
    }

}
