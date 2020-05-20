package com.pwr.it.app.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.pwr.it.app.data.domain.dto.response.ImageUrl;
import com.pwr.it.app.web.exception.file.EmptyFileException;
import com.pwr.it.app.web.exception.file.FileTypeNotSupportedException;
import com.pwr.it.app.web.exception.file.FileValidationException;
import com.pwr.it.app.web.exception.file.MaxFileSizeException;
import io.micronaut.context.annotation.Value;
import io.micronaut.http.MediaType;
import io.micronaut.http.multipart.CompletedFileUpload;

import javax.inject.Singleton;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Singleton
public class UploadImageService {

    private static final List<String> SUPPORTED_TYPES = Arrays.asList(MediaType.IMAGE_JPEG, MediaType.IMAGE_PNG);
    private static final long MAX_FILE_SIZE = 5242880;

    @Value("${cloudinary.cloud.name}")
    private String cloudName;
    @Value("${cloudinary.api.key}")
    private String apiKey;
    @Value("${cloudinary.api.secret}")
    private String apiSecret;


    public ImageUrl upload(CompletedFileUpload image) throws FileValidationException {
        validateFile(image);
        return pushImage(image);
    }

    private void validateFile(CompletedFileUpload image) throws FileValidationException {
        if (image == null || image.getSize() == 0) {
            throw new EmptyFileException();
        }
        if (image.getSize() > MAX_FILE_SIZE) {
            throw new MaxFileSizeException();
        }
        Optional<MediaType> contentType = image.getContentType();
        if (!(contentType.isPresent() && SUPPORTED_TYPES.contains(contentType.get().getName()))) {
            throw new FileTypeNotSupportedException();
        }
    }

    private ImageUrl pushImage(CompletedFileUpload image) throws FileValidationException {
        try {
            return tryToUploadImage(image);
        } catch (IOException e) {
            throw new FileValidationException("Sorry, something bad has happened. Please try again");
        }
    }

    private ImageUrl tryToUploadImage(CompletedFileUpload image) throws IOException {
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret));
        Map result = cloudinary.uploader().upload(image.getBytes(), ObjectUtils.asMap("resource_type", "auto"));
        return translateResult(result);
    }

    private ImageUrl translateResult(Map result) {
        return ImageUrl.builder()
                .url("" + result.get("url"))
                .secureUrl("" + result.get("secure_url"))
                .build();
    }

}
