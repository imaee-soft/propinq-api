package com.imaee.propinq.buildings.data.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
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
    @Column(unique = true)
    private String name;

    private Boolean deleted = false;
}
