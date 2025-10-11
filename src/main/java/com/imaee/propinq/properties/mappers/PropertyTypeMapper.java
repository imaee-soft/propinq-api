package com.imaee.propinq.properties.mappers;

import com.imaee.propinq.properties.controllers.requests.PropertyTypeRequest;
import com.imaee.propinq.properties.controllers.responses.PropertyTypeResponse;
import com.imaee.propinq.properties.data.models.PropertyType;

public class PropertyTypeMapper {
    public static PropertyTypeResponse toPropertyTypeResponse(PropertyType propertyType) {
        return new PropertyTypeResponse(
                propertyType.getPropertyTypeId(),
                propertyType.getName(),
                propertyType.getDescription(),
                propertyType.getDeleted()
        );
    }

    public static PropertyType toPropertyType(PropertyTypeRequest propertyTypeRequest) {
        return PropertyType.builder()
                .name(propertyTypeRequest.name())
                .description(propertyTypeRequest.description())
                .build();

    }
}
