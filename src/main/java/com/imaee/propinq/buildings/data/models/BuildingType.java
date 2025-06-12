package com.imaee.propinq.buildings.data.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.util.UUID;

@Entity(name="building_types")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class BuildingType {
    @Id
    private UUID buildingTypeId = UUID.randomUUID();

    @NonNull
    private String name;

}
