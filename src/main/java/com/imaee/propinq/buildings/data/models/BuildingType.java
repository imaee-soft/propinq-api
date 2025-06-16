package com.imaee.propinq.buildings.data.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;
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

    @OneToMany
    private List<Building> buildings;

}
