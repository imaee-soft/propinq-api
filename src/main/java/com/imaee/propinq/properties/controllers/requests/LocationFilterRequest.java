package com.imaee.propinq.properties.controllers.requests;

import lombok.Data;

@Data
public class LocationFilterRequest {
    private Double latitude;
    private Double longitude;
    private Double radiusKm;
}