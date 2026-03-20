package com.imaee.propinq.properties.services.facades.implementations;

import com.imaee.propinq.properties.data.models.Property;
import com.imaee.propinq.properties.services.facades.interfaces.IPropertyFacade;
import com.imaee.propinq.shared.data.models.Image;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static com.imaee.propinq.buildings.Constants.MAXIMUM_IMAGE_SIZE_MESSAGE;
import static com.imaee.propinq.buildings.Constants.WRONG_IMAGE_FORMAT_MESSAGE;
import static com.imaee.propinq.shared.Constants.ALLOWED_IMAGE_FORMATS;
import static com.imaee.propinq.shared.Constants.MAXIMUM_FILE_SIZE;
import static java.util.Arrays.stream;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Component
@AllArgsConstructor
public class PropertyFacade implements IPropertyFacade {

    @Override
    public List<String> getImagesURLs(Property property) {
        return getImagesURLs(property.getImages());
    }

    @Override
    public List<String> getImagesURLs(List<Image> images) {
        return images.stream()
                .map(Image::getUrl)
                .toList();
    }

    @Override
    public void validatePropertyImages(MultipartFile[] imageFiles){
        stream(imageFiles).forEach(imageFile -> {
            throwExceptionIfImageExceedsMaximumSize(imageFile);
            throwExceptionIfImageHasBadFormat(imageFile);
        });
    }

    private void throwExceptionIfImageExceedsMaximumSize(MultipartFile imageFile) {
        if (imageFile.getSize() > MAXIMUM_FILE_SIZE)
            throw new ResponseStatusException(BAD_REQUEST, MAXIMUM_IMAGE_SIZE_MESSAGE);
    }

    private void throwExceptionIfImageHasBadFormat(MultipartFile imageFile) {
        final var contentType = imageFile.getContentType();
        if (contentType == null || !ALLOWED_IMAGE_FORMATS.contains(contentType))
            throw new ResponseStatusException(BAD_REQUEST, WRONG_IMAGE_FORMAT_MESSAGE);
    }
}
