package com.imaee.propinq.properties.data.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;
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
    private String name;

    @OneToMany
    private List<Property> properties;

}
