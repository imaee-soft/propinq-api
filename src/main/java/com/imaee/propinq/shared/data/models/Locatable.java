package com.imaee.propinq.shared.data.models;

import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import static lombok.AccessLevel.PROTECTED;

@MappedSuperclass
@Data
@SuperBuilder
@NoArgsConstructor(access = PROTECTED)
public abstract class Locatable {
    protected Double latitude;
    protected Double longitude;
}