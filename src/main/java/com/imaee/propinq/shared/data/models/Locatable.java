package com.imaee.propinq.shared.data.models;

import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.NonNull;

@MappedSuperclass
@Data
public abstract class Locatable {

    private Double latitude;

    private Double longitude;

}
