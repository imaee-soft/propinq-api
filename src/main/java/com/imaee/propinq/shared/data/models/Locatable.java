package com.imaee.propinq.shared.data.models;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;



@MappedSuperclass
public abstract class Locatable {

    @Column(name="latitude")
    private Double latitude;

    @Column(name="longitude")
    private Double longitude;

}
