package com.pwr.it.app.web.error;

import com.pwr.it.app.web.exception.DateValidationException;
import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;

import javax.inject.Singleton;

@Produces
@Singleton
@Requires(classes = {DateValidationException.class, ExceptionHandler.class})
public class DateValidationExceptionHandler implements ExceptionHandler<DateValidationException, HttpResponse> {

    @Override
    public HttpResponse handle(HttpRequest request, DateValidationException exception) {
        ApiError apiError = new ApiError("DATE_VALIDATION_ERROR", exception.getMessage());
        return HttpResponse.badRequest(apiError);
    }

}
