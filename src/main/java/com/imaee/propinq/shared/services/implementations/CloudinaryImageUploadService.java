package com.imaee.propinq.shared.services.implementations;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.imaee.propinq.shared.data.models.Image;
import com.imaee.propinq.shared.services.interfaces.IImageUploadService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.imaee.propinq.shared.utils.ExceptionUtils.runCatching;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE;

@AllArgsConstructor
@Service
public class CloudinaryImageUploadService implements IImageUploadService {

    private static final String URL_KEY = "url";
    private static final String PUBLIC_ID_KEY = "public_id";
    private final Cloudinary cloudinary;

    @Override
    public Image uploadImage(MultipartFile file) {
        return runCatching(() -> uploadCloudinaryImage(file), SERVICE_UNAVAILABLE);
    }

    @Override
    public List<Image> uploadImages(MultipartFile[] files) {
        if (files == null || files.length == 0) {
            return Collections.emptyList();
        }

        return stream(files)
                .filter(Objects::nonNull)
                .filter(file -> !file.isEmpty())
                .map(this::uploadImage)
                .collect(toList());
    }

    private Image uploadCloudinaryImage(MultipartFile file) throws IOException {
        final Map<?, ?> uploadResult = cloudinary.uploader()
                .upload(file.getBytes(), ObjectUtils.emptyMap());
        final String url = uploadResult.get(URL_KEY).toString();
        final String publicId = uploadResult.get(PUBLIC_ID_KEY).toString();

        return new Image(url, file.getName(), false, publicId);
    }

    @Override
    public void deleteImages(List<Image> images) {
        if (images == null || images.isEmpty()) {
            return;
        }
        final List<String> publicIds = images.stream()
                .map(Image::getPublicId)
                .collect(Collectors.toList());
        runCatching(() -> {
            cloudinary.api().deleteResources(publicIds, ObjectUtils.emptyMap());
            return null;
        }, SERVICE_UNAVAILABLE);
    }
}