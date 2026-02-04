package com.imaee.propinq.buildings.config;

import com.imaee.propinq.shared.data.models.Image;
import com.imaee.propinq.shared.services.interfaces.IImageUploadService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Provides a stub IImageUploadService for building integration tests. No external storage.
 */
@TestConfiguration
public class TestImageUploadConfig {

    @Bean
    @Primary
    public IImageUploadService testImageUploadService() {
        return new IImageUploadService() {
            @Override
            public Image uploadImage(MultipartFile file) {
                String suffix = UUID.randomUUID().toString();
                return new Image(
                        "https://test.local/img-" + suffix,
                        file.getOriginalFilename() != null ? file.getOriginalFilename() : "image",
                        false,
                        "public-id-" + suffix
                );
            }

            @Override
            public List<Image> uploadImages(MultipartFile[] files) {
                if (files == null || files.length == 0) {
                    return new ArrayList<>();
                }
                List<Image> result = new ArrayList<>();
                for (int i = 0; i < files.length; i++) {
                    String suffix = UUID.randomUUID().toString();
                    result.add(new Image(
                            "https://test.local/img-" + i + "-" + suffix,
                            files[i].getOriginalFilename() != null ? files[i].getOriginalFilename() : "image",
                            false,
                            "public-id-" + i + "-" + suffix
                    ));
                }
                return result;
            }

            @Override
            public void deleteImages(List<Image> images) {
                // no-op
            }
        };
    }
}
