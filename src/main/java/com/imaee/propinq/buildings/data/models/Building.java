package com.imaee.propinq.buildings.data.models;

import com.imaee.propinq.shared.data.models.Image;
import com.imaee.propinq.properties.data.models.Property;
import com.imaee.propinq.shared.data.models.Review;
import com.imaee.propinq.shared.data.models.Locatable;
import com.imaee.propinq.users.data.models.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
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
    @Column(unique = true)
    private String name;

    @NonNull
    private String description;

    @NonNull
    private String address;

    @NonNull
    @ManyToMany(mappedBy = "building")
    private List<Image> images = Collections.emptyList();

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

    private Boolean deleted = false;
}
