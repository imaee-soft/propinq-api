package com.imaee.propinq.properties.services.usecases.interfaces;

import com.imaee.propinq.properties.controllers.requests.UpdatePropertyRequest;
import com.imaee.propinq.properties.controllers.responses.PropertyDetailsResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface IUpdatePropertyUseCase {
    PropertyDetailsResponse updateProperty(UUID propertyId, UpdatePropertyRequest updatePropertyRequest, MultipartFile[] imageFiles);
}
