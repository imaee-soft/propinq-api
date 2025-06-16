package com.imaee.propinq.buildings.data.models;

import com.imaee.propinq.shared.data.models.Image;
import com.imaee.propinq.properties.data.models.Property;
import com.imaee.propinq.shared.data.models.Review;
import jakarta.persistence.*;
import lombok.*;
import com.imaee.propinq.shared.data.models.Locatable;
import com.imaee.propinq.users.data.models.User;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Entity(name = "buildings")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Building extends Locatable {

    @Id
    private UUID buildingId = UUID.randomUUID();

    @NonNull
    private String name;

    @NonNull
    private String description;

    @NonNull
    private String address;

    @NonNull
    @ManyToMany(mappedBy = "building", cascade = CascadeType.ALL)
    private List<Image> images;

    @OneToMany(mappedBy = "building", cascade = CascadeType.ALL)
    private List<Property> properties = Collections.emptyList();

    @NonNull
    @ManyToOne
    private User user;

    @NonNull
    @ManyToOne
    private BuildingType buildingType;

    @OneToMany(mappedBy = "building", cascade = CascadeType.ALL)
    private List<Review> reviews = Collections.emptyList();

}
