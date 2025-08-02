package com.imaee.propinq.shared.services.interfaces;

import com.imaee.propinq.shared.data.models.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageUploadService {
    Image uploadImage(MultipartFile file);
    List<Image> uploadImages(MultipartFile[] files);
    void deleteImages(List<Image> images);

}