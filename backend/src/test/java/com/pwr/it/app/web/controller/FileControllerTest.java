package com.pwr.it.app.web.controller;

import com.pwr.it.app.data.domain.dto.response.ImageUrl;
import com.pwr.it.app.services.UploadImageService;
import com.pwr.it.app.web.exception.file.EmptyFileException;
import com.pwr.it.app.web.exception.file.FileTypeNotSupportedException;
import com.pwr.it.app.web.exception.file.FileValidationException;
import com.pwr.it.app.web.exception.file.MaxFileSizeException;
import io.micronaut.context.annotation.Property;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.http.client.multipart.MultipartBody;
import io.micronaut.test.annotation.MicronautTest;
import io.micronaut.test.annotation.MockBean;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@MicronautTest
@Property(name = "micronaut.server.port", value = "-1")
@Property(name = "micronaut.security.enabled", value = "false")
public class FileControllerTest {

    private static final ImageUrl IMAGE_URL = new ImageUrl("http://simple.url", "https://secured.url");
    private static final MultipartBody IMAGE_OBJECT = MultipartBody.builder()
            .addPart("image", "image.jpg", MediaType.MULTIPART_FORM_DATA_TYPE, new byte[0]).build();

    @Inject
    @Client("/")
    RxHttpClient client;

    @Inject
    UploadImageService uploadImageService;

    @Test
    @DisplayName("When user uploads image then OK message should be return")
    void shouldReturnCorrectMessage() throws FileValidationException {
        when(uploadImageService.upload(any())).thenReturn(IMAGE_URL);

        HttpResponse response = client.toBlocking()
                .exchange(HttpRequest.POST("/api/file/image/upload", IMAGE_OBJECT)
                        .contentType(MediaType.MULTIPART_FORM_DATA));

        assertAll(
                () -> assertEquals(HttpStatus.OK, response.getStatus())
        );
    }

    @Test
    @DisplayName("When user uploads image with no content then should return error")
    void shouldReturnEmptyFileErrorWhenImageIsEmpty() throws FileValidationException {
        when(uploadImageService.upload(any())).thenThrow(new EmptyFileException());

        try {
            client.toBlocking()
                    .exchange(HttpRequest.POST("/api/file/image/upload", IMAGE_OBJECT)
                            .contentType(MediaType.MULTIPART_FORM_DATA));
        } catch (HttpClientResponseException e) {
            assertAll(
                    () -> assertEquals(HttpStatus.BAD_REQUEST, e.getStatus()),
                    () -> assertEquals("Empty file sent. Select an existing file", e.getMessage())
            );
            return;
        }

        throw new IllegalStateException("HttpClientResponseException exception should be thrown");
    }

    @Test
    @DisplayName("When user uploads image with not supported content then should return error")
    void shouldReturnTypeNotSupportedErrorWhenImageFormatIsWrong() throws FileValidationException {
        when(uploadImageService.upload(any())).thenThrow(new FileTypeNotSupportedException());

        try {
            client.toBlocking()
                    .exchange(HttpRequest.POST("/api/file/image/upload", IMAGE_OBJECT)
                            .contentType(MediaType.MULTIPART_FORM_DATA));
        } catch (HttpClientResponseException e) {
            assertAll(
                    () -> assertEquals(HttpStatus.BAD_REQUEST, e.getStatus()),
                    () -> assertEquals("Sorry, this file type is not supported. Choose another file", e.getMessage())
            );
            return;
        }

        throw new IllegalStateException("HttpClientResponseException exception should be thrown");
    }

    @Test
    @DisplayName("When user uploads image with not supported content then should return error")
    void shouldReturnFileSizeExceededErrorWhenImageIsToBig() throws FileValidationException {
        when(uploadImageService.upload(any())).thenThrow(new MaxFileSizeException());

        try {
            client.toBlocking()
                    .exchange(HttpRequest.POST("/api/file/image/upload", IMAGE_OBJECT)
                            .contentType(MediaType.MULTIPART_FORM_DATA));
        } catch (HttpClientResponseException e) {
            assertAll(
                    () -> assertEquals(HttpStatus.BAD_REQUEST, e.getStatus()),
                    () -> assertEquals("The size of this file exceeds the maximum size allowed. Use a smaller file", e.getMessage())
            );
            return;
        }

        throw new IllegalStateException("HttpClientResponseException exception should be thrown");
    }

    @MockBean(UploadImageService.class)
    UploadImageService uploadImageService() {
        return mock(UploadImageService.class);
    }

}
