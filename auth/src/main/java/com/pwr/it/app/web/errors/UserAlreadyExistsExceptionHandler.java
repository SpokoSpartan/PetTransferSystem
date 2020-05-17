package com.pwr.it.app.web.errors;

import com.pwr.it.app.web.exception.UserAlreadyExistsException;
import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;

import javax.inject.Singleton;

@Produces
@Singleton
@Requires(classes = {UserAlreadyExistsException.class, ExceptionHandler.class})
public class UserAlreadyExistsExceptionHandler implements ExceptionHandler<UserAlreadyExistsException, HttpResponse> {

    @Override
    public HttpResponse handle(HttpRequest request, UserAlreadyExistsException exception) {
        ApiError apiError = new ApiError("USER_NOT_FOUND", exception.getMessage());
        return HttpResponse.notFound(apiError);
    }

}
