package com.imaee.propinq.buildings.services.usecases.implementations;

import com.imaee.propinq.buildings.controllers.requests.CreateBuildingRequest;
import com.imaee.propinq.buildings.data.models.Building;
import com.imaee.propinq.buildings.data.repositories.IBuildingRepository;
import com.imaee.propinq.buildings.services.facades.interfaces.IBuildingFacade;
import com.imaee.propinq.buildings.services.usecases.interfaces.ICreateBuildingUseCase;
import com.imaee.propinq.shared.services.interfaces.IImageUploadService;
import com.imaee.propinq.users.services.interfaces.IUserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import static com.imaee.propinq.buildings.mappers.BuildingMapper.toBuilding;

@Component
@AllArgsConstructor
public class CreateBuildingUseCase implements ICreateBuildingUseCase {

    private final IBuildingRepository buildingRepository;
    private final IUserService userService;
    private final IImageUploadService fileUploadService;
    private final IBuildingFacade buildingFacade;

    @Override
    @Transactional
    public void createBuilding(CreateBuildingRequest createBuildingRequest, MultipartFile[] imageFiles) {
        buildingFacade.throwExceptionIfBuildingExistsByName(createBuildingRequest);
        buildingFacade.validateBuildingImages(imageFiles);
        buildingRepository.save(buildBuildingEntity(createBuildingRequest, imageFiles));
    }

    private Building buildBuildingEntity(CreateBuildingRequest createBuildingRequest, MultipartFile[] imageFiles) {
        final var user = userService.findUserById(createBuildingRequest.userId());
        final var images = fileUploadService.uploadImages(imageFiles);
        return toBuilding(createBuildingRequest, user, images);
    }
}