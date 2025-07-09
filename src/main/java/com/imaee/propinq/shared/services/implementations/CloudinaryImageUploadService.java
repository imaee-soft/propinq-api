package com.imaee.propinq.shared.services.implementations;

import com.cloudinary.Cloudinary;
import com.imaee.propinq.shared.data.models.Image;
import com.imaee.propinq.shared.services.interfaces.IFileUploadService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.imaee.propinq.shared.utils.ExceptionUtils.runCatching;
import static java.util.Arrays.stream;
import static java.util.Collections.emptyMap;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE;

@AllArgsConstructor
@Service
public class CloudinaryImageUploadService implements IFileUploadService {

    private static final String URL_KEY = "url";
    private final Cloudinary cloudinary;

    @Override
    public Image uploadImage(MultipartFile file) {
        return runCatching(
                () -> uploadCloudinaryImage(file),
                SERVICE_UNAVAILABLE
        );
    }

    @Override
    public List<Image> uploadImages(MultipartFile[] files) {
        return stream(files)
                .map(this::uploadImage)
                .collect(toList());
    }

    private Image uploadCloudinaryImage(MultipartFile file) throws IOException {
        final var url = cloudinary.uploader()
                .upload(file.getBytes(), emptyMap())
                .get(URL_KEY)
                .toString();
        System.out.println("URL DE LA IMAGEN: " + url);
        return new Image(url, file.getName(), false);
    }
}