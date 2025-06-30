package com.imaee.propinq.buildings.data.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity(name="building_types")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class BuildingType {
    @Id
    @Builder.Default
    private UUID buildingTypeId = UUID.randomUUID();

    @NotNull
    private String name;

    @OneToMany
    private List<Building> buildings;
    
    @Builder.Default
    private Boolean deleted = false;

}
