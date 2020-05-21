package com.pwr.it.app.web.controller;

import com.pwr.it.app.data.domain.dto.response.ImageUrl;
import com.pwr.it.app.services.UploadImageService;
import com.pwr.it.app.web.exception.file.FileValidationException;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.multipart.CompletedFileUpload;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "File")
@Controller("/api/file")
@RequiredArgsConstructor
@Secured(SecurityRule.IS_AUTHENTICATED)
public class FileController {

    private final UploadImageService uploadImageService;

    @Operation(
            summary = "Upload image",
            description = "Endpoint allows to update image in jpeg and png format. Max size cannot exceed 5.24 MB."
    )
    @Post(value = "/image/upload", consumes = MediaType.MULTIPART_FORM_DATA)
    public ImageUrl uploadFile(@Schema(format = "binary", type = "string") CompletedFileUpload image) throws FileValidationException {
        return uploadImageService.upload(image);
    }

}
