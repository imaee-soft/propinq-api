package com.imaee.propinq.buildings.services.usecases.implementations;

import com.imaee.propinq.buildings.controllers.requests.UpdateBuildingRequest;
import com.imaee.propinq.buildings.controllers.responses.BuildingDetailsResponse;
import com.imaee.propinq.buildings.data.models.Building;
import com.imaee.propinq.buildings.data.repositories.IBuildingRepository;
import com.imaee.propinq.buildings.mappers.BuildingMapper;
import com.imaee.propinq.buildings.services.facades.interfaces.IBuildingFacade;
import com.imaee.propinq.buildings.services.usecases.interfaces.IFindBuildingByIdUseCase;
import com.imaee.propinq.buildings.services.usecases.interfaces.IUpdateBuildingUseCase;
import com.imaee.propinq.shared.services.interfaces.IImageUploadService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.util.UUID;

@Component
@AllArgsConstructor
public class UpdateBuildingUseCase implements IUpdateBuildingUseCase {

    private final IBuildingRepository buildingRepository;
    private final IImageUploadService fileUploadService;
    private final IFindBuildingByIdUseCase findBuildingByIdUseCase;
    private final IBuildingFacade buildingFacade;

    @Override
    @Transactional
    public BuildingDetailsResponse updateBuilding(UUID buildingId, UpdateBuildingRequest updateBuildingRequest, MultipartFile[] imageFiles) {
        Building building = findBuildingByIdUseCase.findBuilding(buildingId);
        buildingFacade.throwExceptionIfBuildingExistsByName(updateBuildingRequest);
        buildingFacade.validateBuildingImages(imageFiles);
        final var images = fileUploadService.uploadImages(imageFiles);
        building.setName(updateBuildingRequest.name());
        building.setDescription(updateBuildingRequest.description());
        building.setImages(images);
        return BuildingMapper.toBuildingDetailsResponse(buildingRepository.save(building), buildingFacade.getImagesURLs(images));
    }
}
