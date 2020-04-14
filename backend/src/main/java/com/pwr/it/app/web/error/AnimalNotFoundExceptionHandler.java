package com.pwr.it.app.web.error;

import com.pwr.it.app.web.exception.AnimalNotFoundException;
import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;

import javax.inject.Singleton;

@Produces
@Singleton
@Requires(classes = {AnimalNotFoundException.class, ExceptionHandler.class})
public class AnimalNotFoundExceptionHandler implements ExceptionHandler<AnimalNotFoundException, HttpResponse> {

    @Override
    public HttpResponse handle(HttpRequest request, AnimalNotFoundException exception) {
        ApiError apiError = new ApiError("ANIMAL_NOT_FOUND", exception.getMessage());
        return HttpResponse.notFound(apiError);
    }

}
