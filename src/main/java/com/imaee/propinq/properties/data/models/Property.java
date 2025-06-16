package com.imaee.propinq.properties.data.models;

import com.imaee.propinq.buildings.data.models.Building;
import com.imaee.propinq.buildings.data.models.BuildingType;
import com.imaee.propinq.shared.data.models.Review;
import jakarta.persistence.*;
import lombok.*;
import com.imaee.propinq.shared.data.models.Image;
import com.imaee.propinq.shared.data.models.Locatable;


import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Entity(name="properties")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Property extends Locatable {

    @Id
    private final UUID propertyId = UUID.randomUUID();

    @NonNull
    private String address;

    @ManyToOne
    private Building building;

    @NonNull
    @ManyToMany(mappedBy = "properties")
    private List<Image> images;

    @NonNull
    @ManyToOne
    private PropertyType propertyType;

    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL)
    private List<Review> reviews = Collections.emptyList();
}
