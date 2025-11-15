package com.imaee.propinq.buildings.controllers.requests;

import com.imaee.propinq.properties.controllers.requests.AttributeFilterRequest;
import lombok.Data;

@Data
public class BuildingPropertiesFilter {
    private AttributeFilterRequest attributes;
}
