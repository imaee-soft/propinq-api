package com.imaee.propinq.buildings.services.usecases.implementations;

import com.imaee.propinq.buildings.controllers.requests.BuildingRequest;
import com.imaee.propinq.buildings.data.models.Building;
import com.imaee.propinq.buildings.data.repositories.IBuildingRepository;
import com.imaee.propinq.buildings.services.usecases.interfaces.ICreateBuildingUseCase;
import com.imaee.propinq.shared.services.interfaces.IFileUploadService;
import com.imaee.propinq.users.services.interfaces.IUserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import static com.imaee.propinq.buildings.Constants.EXISTING_NAME_MESSAGE;
import static com.imaee.propinq.buildings.Constants.MAXIMUM_IMAGE_SIZE_MESSAGE;
import static com.imaee.propinq.buildings.Constants.WRONG_IMAGE_FORMAT_MESSAGE;
import static com.imaee.propinq.buildings.mappers.BuildingMapper.toBuilding;
import static com.imaee.propinq.shared.Constants.ALLOWED_IMAGE_FORMATS;
import static com.imaee.propinq.shared.Constants.MAXIMUM_FILE_SIZE;
import static java.util.Arrays.stream;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Component
@AllArgsConstructor
public class CreateBuildingUseCase implements ICreateBuildingUseCase {

    private final IBuildingRepository buildingRepository;
    private final IUserService userService;
    private final IFileUploadService fileUploadService;

    @Override
    @Transactional
    public void createBuilding(BuildingRequest buildingRequest, MultipartFile[] imageFiles) {
        throwExceptionIfBuildingExistsByName(buildingRequest);
        validateBuildingImages(imageFiles);
        buildingRepository.save(buildBuildingEntity(buildingRequest, imageFiles));
    }

    private void throwExceptionIfBuildingExistsByName(BuildingRequest buildingRequest) {
        if (buildingRepository.existsByName(buildingRequest.name()))
            throw new ResponseStatusException(BAD_REQUEST, EXISTING_NAME_MESSAGE);
    }

    private void validateBuildingImages(MultipartFile[] imageFiles) {
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

    private Building buildBuildingEntity(BuildingRequest request, MultipartFile[] imageFiles) {
        final var user = userService.findUserById(request.userId());
        final var images = fileUploadService.uploadImages(imageFiles);
        return toBuilding(request, user, images);
    }
}