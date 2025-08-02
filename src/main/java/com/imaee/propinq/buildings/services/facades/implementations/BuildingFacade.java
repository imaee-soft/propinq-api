package com.imaee.propinq.buildings.services.facades.implementations;

import com.imaee.propinq.buildings.controllers.requests.CreateBuildingRequest;
import com.imaee.propinq.buildings.data.models.Building;
import com.imaee.propinq.buildings.data.repositories.IBuildingRepository;
import com.imaee.propinq.buildings.services.facades.interfaces.IBuildingFacade;
import com.imaee.propinq.shared.data.models.Image;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static com.imaee.propinq.buildings.Constants.EXISTING_NAME_MESSAGE;
import static com.imaee.propinq.buildings.Constants.MAXIMUM_IMAGE_SIZE_MESSAGE;
import static com.imaee.propinq.buildings.Constants.WRONG_IMAGE_FORMAT_MESSAGE;
import static com.imaee.propinq.shared.Constants.ALLOWED_IMAGE_FORMATS;
import static com.imaee.propinq.shared.Constants.MAXIMUM_FILE_SIZE;
import static java.util.Arrays.stream;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Component
@AllArgsConstructor
public class BuildingFacade implements IBuildingFacade {

    private final IBuildingRepository buildingRepository;

    @Override
    public void validateBuildingImages(MultipartFile[] imageFiles) {
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

    @Override
    public void throwExceptionIfBuildingExistsByName(CreateBuildingRequest request) {
        if (buildingRepository.existsByName(request.name()))
            throw new ResponseStatusException(BAD_REQUEST, EXISTING_NAME_MESSAGE);
    }

    @Override
    public List<String> getImagesURLs(Building building) {
        return getImagesURLs(building.getImages());
    }

    @Override
    public List<String> getImagesURLs(List<Image> images) {
        return images.stream()
                .map(Image::getUrl)
                .toList();
    }
}
