package com.imaee.propinq.properties.services.usecases.implementations;

import com.imaee.propinq.buildings.services.usecases.interfaces.IFindBuildingByIdUseCase;
import com.imaee.propinq.properties.controllers.requests.CreatePropertyRequest;
import com.imaee.propinq.properties.data.models.Property;
import com.imaee.propinq.properties.data.repositories.IPropertyRepository;
import com.imaee.propinq.properties.services.usecases.interfaces.ICreatePropertyUseCase;
import com.imaee.propinq.shared.services.interfaces.IImageUploadService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import static com.imaee.propinq.properties.Constants.EXISTING_NUMBER_MESSAGE;
import static com.imaee.propinq.properties.mappers.PropertyMapper.toApartment;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
@AllArgsConstructor
public class CreateApartmentUseCase implements ICreatePropertyUseCase {

    private final IPropertyRepository propertyRepository;
    private final IFindBuildingByIdUseCase findBuildingByIdUseCase;
    private final IImageUploadService fileUploadService;

    @Override
    public void createProperty(CreatePropertyRequest request, MultipartFile[] imageFiles) {
        throwExceptionIfApartmentExistsByNumber(request);
        propertyRepository.save(buildApartment(request, imageFiles));
    }

    private void throwExceptionIfApartmentExistsByNumber(CreatePropertyRequest request) {
        if (propertyRepository.existsByApartmentNumberAndBuildingBuildingId(request.number(), request.buildingId()))
            throw new ResponseStatusException(BAD_REQUEST, EXISTING_NUMBER_MESSAGE);
    }

    private Property buildApartment(CreatePropertyRequest request, MultipartFile[] imageFiles) {
        final var images = fileUploadService.uploadImages(imageFiles);
        final var building = findBuildingByIdUseCase.findBuilding(request.buildingId());
        return toApartment(request, images, building);
    }
}