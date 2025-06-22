package com.imaee.propinq.shared.data.models;

import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.NonNull;

@MappedSuperclass
@Data
public abstract class Locatable {

    @NonNull
    private Double latitude;

    @NonNull
    private Double longitude;

}
