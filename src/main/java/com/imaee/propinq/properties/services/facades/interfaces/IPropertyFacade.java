package com.imaee.propinq.properties.services.facades.interfaces;

import com.imaee.propinq.properties.data.models.Property;
import com.imaee.propinq.shared.data.models.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IPropertyFacade {
    void validatePropertyImages(MultipartFile[] imageFiles);

    List<String> getImagesURLs(Property property);

    List<String> getImagesURLs(List<Image> images);
}
