package com.imaee.propinq.buildings.services.usecases.implementations;

import com.imaee.propinq.buildings.controllers.requests.UpdateBuildingRequest;
import com.imaee.propinq.buildings.controllers.responses.BuildingDetailsResponse;
import com.imaee.propinq.buildings.data.models.Building;
import com.imaee.propinq.buildings.data.repositories.IBuildingRepository;
import com.imaee.propinq.buildings.mappers.BuildingMapper;
import com.imaee.propinq.buildings.services.facades.interfaces.IBuildingFacade;
import com.imaee.propinq.buildings.services.usecases.interfaces.IFindBuildingByIdUseCase;
import com.imaee.propinq.buildings.services.usecases.interfaces.IUpdateBuildingUseCase;
import com.imaee.propinq.shared.data.models.Image;
import com.imaee.propinq.shared.services.interfaces.IImageUploadService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
        if (isNonNullAndNotEmpty(imageFiles)) {
            buildingFacade.validateBuildingImages(imageFiles);
        }

        List<Image> imagesToDelete = new ArrayList<>();
        List<Image> remainingImages = new ArrayList<>();
        List<String> existingUrlsToKeep = updateBuildingRequest.existingImagesURLS();

        for (Image image : building.getImages()) {
            if (existingUrlsToKeep == null || !existingUrlsToKeep.contains(image.getUrl())) {
                imagesToDelete.add(image);
            } else {
                remainingImages.add(image);
            }
        }

        if (!imagesToDelete.isEmpty()) {
            fileUploadService.deleteImages(imagesToDelete);
        }

        List<Image> newUploadedImages = Collections.emptyList();
        if (isNonNullAndNotEmpty(imageFiles)) {
            newUploadedImages = fileUploadService.uploadImages(imageFiles);
        }
        List<Image> finalImages = new ArrayList<>(remainingImages);
        finalImages.addAll(newUploadedImages);

        building.setName(updateBuildingRequest.name());
        building.setDescription(updateBuildingRequest.description());
        building.setImages(finalImages);

        Building updatedBuilding = buildingRepository.save(building);
        List<String> finalImageUrls = buildingFacade.getImagesURLs(updatedBuilding.getImages());
        return BuildingMapper.toBuildingDetailsResponse(updatedBuilding, finalImageUrls);
    }

    private boolean isNonNullAndNotEmpty(MultipartFile[] imageFiles) {
        return imageFiles != null && imageFiles.length > 0;
    }
}
