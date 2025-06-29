package com.imaee.propinq.properties.data.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
import java.util.UUID;

@Entity(name="property_types")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class PropertyType {
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "property_type_id", updatable = false, nullable = false)
    private UUID id;

    @NonNull
    private String name;

    @NonNull
    private String description;

    @Column(nullable = false)
    @Builder.Default
    private boolean deleted = false;


    @OneToMany
    private List<Property> properties;

}
