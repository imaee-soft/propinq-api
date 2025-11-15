package com.imaee.propinq.properties.controllers.requests;

import lombok.Data;

@Data
public class PoiFilterRequest {
    private String poiType;
    private Double radiusKm;
    private Double north;
    private Double south;
    private Double east;
    private Double west;
    private Integer limit;
}