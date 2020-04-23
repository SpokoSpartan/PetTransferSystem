package com.pwr.it.app.web.controller;

import com.pwr.it.app.data.domain.dto.response.ImageUrl;
import com.pwr.it.app.services.UploadImageService;
import com.pwr.it.app.web.exception.file.FileValidationException;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.multipart.CompletedFileUpload;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "File")
@Controller("/api/file")
@RequiredArgsConstructor
public class FileController {

    private final UploadImageService uploadImageService;

    @Post(value = "/image/upload", consumes = MediaType.MULTIPART_FORM_DATA)
    public ImageUrl uploadFile(CompletedFileUpload image) throws FileValidationException {
        return uploadImageService.upload(image);
    }

}
