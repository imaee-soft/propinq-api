package com.imaee.propinq.properties.controllers.requests;

import com.imaee.propinq.buildings.data.enums.BuildingType;
import lombok.Data;

@Data
public class PropertyFilterRequest {
    private BuildingType buildingType;
    private Double priceMin;
    private Double priceMax;
    private Integer floor;
    private Integer bedrooms;
    private Integer bathrooms;
    private Boolean petsAllowed;
    private Double areaMin;
    private Double areaMax;
}
