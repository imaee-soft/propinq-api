package com.imaee.propinq.properties.services.usecases.implementations;

import com.imaee.propinq.properties.controllers.requests.CreatePropertyRequest;
import com.imaee.propinq.properties.data.models.Property;
import com.imaee.propinq.properties.data.repositories.IPropertyRepository;
import com.imaee.propinq.properties.services.usecases.interfaces.ICreatePropertyUseCase;
import com.imaee.propinq.shared.services.interfaces.IImageUploadService;
import com.imaee.propinq.users.services.usecases.interfaces.IFindUserUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import static com.imaee.propinq.properties.Constants.EXISTING_ADDRESS_MESSAGE;
import static com.imaee.propinq.properties.mappers.PropertyMapper.toHouse;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
@AllArgsConstructor
public class CreateHouseUseCase implements ICreatePropertyUseCase {

    private final IPropertyRepository propertyRepository;
    private final IImageUploadService fileUploadService;
    private final IFindUserUseCase findUserUseCase;

    @Override
    public void createProperty(CreatePropertyRequest request, MultipartFile[] imageFiles) {
        throwExceptionIfPropertyExistsByAddress(request);
        propertyRepository.save(buildHouse(request, imageFiles));
    }

    private void throwExceptionIfPropertyExistsByAddress(CreatePropertyRequest request) {
        if (propertyRepository.existsByLatitudeAndLongitude(request.latitude(), request.longitude()))
            throw new ResponseStatusException(BAD_REQUEST, EXISTING_ADDRESS_MESSAGE);
    }

    private Property buildHouse(CreatePropertyRequest request, MultipartFile[] imageFiles) {
        final var images = fileUploadService.uploadImages(imageFiles);
        final var user = findUserUseCase.findUserById(request.userId());
        return toHouse(request, images, user);
    }
}