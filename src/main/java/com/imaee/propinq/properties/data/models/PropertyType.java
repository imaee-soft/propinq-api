package com.imaee.propinq.properties.data.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Entity(name="property_types")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class PropertyType {
    @Id
    private UUID propertyTypeId = UUID.randomUUID();

    @NotNull
    @Column(unique = true)
    private String name;

    @NotNull
    private String description;

    @NotNull
    @Builder.Default
    private Boolean deleted = false;
  
}
