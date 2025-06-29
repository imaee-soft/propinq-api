package com.imaee.propinq.properties.mappers;

import com.imaee.propinq.properties.controllers.requests.PropertyTypeRequest;
import com.imaee.propinq.properties.controllers.responses.PropertyTypeResponse;
import com.imaee.propinq.properties.data.models.PropertyType;

import java.util.Locale;

public class PropertyTypeMapper {

    public static PropertyTypeResponse toPropertyTypeResponse(PropertyType propertyType) {
        return new PropertyTypeResponse(
                propertyType.getId(),
                propertyType.getName(),
                propertyType.getDescription(),
                propertyType.isDeleted()
        );
    }

    public static PropertyType toPropertyType(PropertyTypeRequest propertyTypeRequest) {
        return PropertyType.builder()
                .name(propertyTypeRequest.name())
                .description(propertyTypeRequest.description())
                .build();

    }
}
