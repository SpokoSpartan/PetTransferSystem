package com.pwr.it.app.services;

import com.pwr.it.app.web.exception.file.EmptyFileException;
import com.pwr.it.app.web.exception.file.FileTypeNotSupportedException;
import com.pwr.it.app.web.exception.file.MaxFileSizeException;
import io.micronaut.context.annotation.Property;
import io.micronaut.http.MediaType;
import io.micronaut.http.multipart.CompletedFileUpload;
import io.micronaut.test.annotation.MicronautTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@MicronautTest
@Property(name = "micronaut.server.port", value = "-1")
@Property(name = "micronaut.security.enabled", value = "false")
public class UploadImageServiceTest {

    private static final CompletedFileUpload IMAGE = mock(CompletedFileUpload.class);

    @Inject
    UploadImageService uploadImageService;

    @Test
    @DisplayName("Should throw empty file exception when file is empty")
    void shouldThrowEmptyFileExceptionWhenFileIsEmpty() {
        when(IMAGE.getSize()).thenReturn(0L);

        Throwable thrown = assertThrows(
                EmptyFileException.class,
                () -> uploadImageService.upload(IMAGE),
                "Expected update() to throw EmptyFileException"
        );

        assertEquals("Empty file sent. Select an existing file", thrown.getMessage());
    }

    @Test
    @DisplayName("Should throw file max size exception when file is too big")
    void shouldThrowFileSizeExceptionWhenFileIsToBig() {
        when(IMAGE.getSize()).thenReturn(5242881L);

        Throwable thrown = assertThrows(
                MaxFileSizeException.class,
                () -> uploadImageService.upload(IMAGE),
                "Expected update() to throw MaxFileSizeException"
        );

        assertEquals("The size of this file exceeds the maximum size allowed. Use a smaller file", thrown.getMessage());
    }

    @Test
    @DisplayName("Should throw content type exception when file tupe is not supported")
    void shouldThrowContentTypeExceptionWhenFileTypeIsNotSupported() {
        when(IMAGE.getSize()).thenReturn(524287L);
        when(IMAGE.getContentType()).thenReturn(Optional.of(new MediaType(MediaType.APPLICATION_XML)));

        Throwable thrown = assertThrows(
                FileTypeNotSupportedException.class,
                () -> uploadImageService.upload(IMAGE),
                "Expected update() to throw FileTypeNotSupportedException"
        );

        assertEquals("Sorry, this file type is not supported. Choose another file", thrown.getMessage());
    }

}
