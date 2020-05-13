package com.pwr.it.app.web.error;

import com.pwr.it.app.web.exception.UserNotFoundException;
import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;

import javax.inject.Singleton;

@Produces
@Singleton
@Requires(classes = {UserNotFoundException.class, ExceptionHandler.class})
public class UserNotFoundExceptionHandler implements ExceptionHandler<UserNotFoundException, HttpResponse> {

    @Override
    public HttpResponse handle(HttpRequest request, UserNotFoundException exception) {
        ApiError apiError = new ApiError("USER_NOT_FOUND", exception.getMessage());
        return HttpResponse.notFound(apiError);
    }

}
