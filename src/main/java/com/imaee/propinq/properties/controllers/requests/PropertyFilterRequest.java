package com.imaee.propinq.properties.controllers.requests;

import lombok.Data;

@Data
public class PropertyFilterRequest {
    private AttributeFilterRequest attributes;
    private LocationFilterRequest location;
    private PoiFilterRequest poi;
}
