package com.imaee.propinq.properties.data.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Data;


import java.util.List;
import java.util.UUID;

@Entity(name="property_types")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class PropertyType {
    @Id
    @Builder.Default
    private UUID propertyTypeId = UUID.randomUUID();

    @NotNull
    private String name;

    @OneToMany
    private List<Property> properties;

}
