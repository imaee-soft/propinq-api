package com.imaee.propinq.properties.controllers.implementations;

import com.imaee.propinq.properties.controllers.interfaces.IPropertyController;
import com.imaee.propinq.properties.controllers.requests.PropertyFilterRequest;
import com.imaee.propinq.properties.controllers.requests.CreatePropertyRequest;
import com.imaee.propinq.properties.controllers.responses.PropertyDetailsResponse;
import com.imaee.propinq.properties.controllers.responses.PropertyResponse;
import com.imaee.propinq.properties.services.interfaces.IPropertyService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
public class PropertyController implements IPropertyController {

    private final IPropertyService propertyService;

    @Override
    public List<PropertyResponse> getProperties(PropertyFilterRequest filter) {
        return propertyService.getProperties(filter);
    }

    @Override
    public PropertyDetailsResponse getPropertyDetails(UUID propertyId) {
        return propertyService.getPropertyDetails(propertyId);
    }

    @Override
    public void createProperty(CreatePropertyRequest createPropertyRequest, MultipartFile[] imageFiles) {
        propertyService.createProperty(createPropertyRequest, imageFiles);
    }

    @Override
    public void deleteProperty(UUID propertyId) {
        propertyService.deleteProperty(propertyId);
    }

    @Override
    public void restoreProperty( UUID propertyId){
        propertyService.restoreProperty(propertyId);
    }

}