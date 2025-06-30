package com.imaee.propinq.properties.data.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import java.util.UUID;

@Entity(name="property_types")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class PropertyType {
    @Id

    private UUID propertyTypeId = UUID.randomUUID();

    @NonNull
    @Column(unique = true)
    private String name;

    @NonNull
    private String description;

    private Boolean deleted = false;
  
}
