package com.imaee.propinq.properties.services.usecases.implementations;

import com.imaee.propinq.properties.controllers.requests.UpdatePropertyRequest;
import com.imaee.propinq.properties.controllers.responses.PropertyDetailsResponse;
import com.imaee.propinq.properties.data.models.Property;
import com.imaee.propinq.properties.data.repositories.IPropertyRepository;
import com.imaee.propinq.properties.mappers.PropertyMapper;
import com.imaee.propinq.properties.services.facades.interfaces.IPropertyFacade;
import com.imaee.propinq.properties.services.usecases.interfaces.IFindPropertyByIdUseCase;
import com.imaee.propinq.properties.services.usecases.interfaces.IUpdatePropertyUseCase;
import com.imaee.propinq.shared.data.models.Image;
import com.imaee.propinq.shared.services.interfaces.IImageUploadService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Component
@AllArgsConstructor
public class UpdatePropertyUseCase implements IUpdatePropertyUseCase {

    private final IPropertyRepository propertyRepository;
    private final IFindPropertyByIdUseCase findPropertyByIdUseCase;
    private final IPropertyFacade propertyFacade;
    private final IImageUploadService fileUploadService;

    @Override
    public PropertyDetailsResponse updateProperty(UUID propertyId, UpdatePropertyRequest updatePropertyRequest, MultipartFile[] imageFiles){
        Property property = findPropertyByIdUseCase.findProperty(propertyId);
        if (isNonNullAndNotEmpty(imageFiles)) {
            propertyFacade.validatePropertyImages(imageFiles);
        }

        List<Image> imagesToDelete = new ArrayList<>();
        List<Image> remainingImages = new ArrayList<>();
        List<String> existingUrlsToKeep = updatePropertyRequest.existingImagesURLS();

        for (Image image : property.getImages()) {
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

        property.setTitle(updatePropertyRequest.title());
        property.setDescription(updatePropertyRequest.description());
        property.setImages(finalImages);
        property.setPrice(updatePropertyRequest.price());
        property.setBedrooms(updatePropertyRequest.bedrooms());
        property.setBathrooms(updatePropertyRequest.bathrooms());
        property.setPetsAllowed(updatePropertyRequest.petsAllowed());
        property.setFurnishing(updatePropertyRequest.furnishing());
        property.setExpenses(updatePropertyRequest.expenses());

        Property updatedProperty = propertyRepository.save(property);
        List<String> finalImageUrls = propertyFacade.getImagesURLs(updatedProperty.getImages());
        return PropertyMapper.toPropertyDetailsResponse(updatedProperty, finalImageUrls);
    }

    private boolean isNonNullAndNotEmpty(MultipartFile[] imageFiles) {
        return imageFiles != null && imageFiles.length > 0;
    }

}
