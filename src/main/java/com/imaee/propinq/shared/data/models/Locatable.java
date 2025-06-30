package com.imaee.propinq.shared.data.models;

import jakarta.persistence.MappedSuperclass;
import lombok.Data;


@MappedSuperclass
@Data
public abstract class Locatable {

    private Double latitude;

    private Double longitude;

}
