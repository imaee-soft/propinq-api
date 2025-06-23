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
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @NotNull
    private String name;

    @NotNull
    private String description;

    @NotNull
    private String address;

    @NotNull
    @ManyToMany(mappedBy = "building", cascade = CascadeType.ALL)
    private List<Image> images;

    @OneToMany(mappedBy = "building", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Property> properties = Collections.emptyList();

    @NotNull
    @ManyToOne
    private User user;

    @NotNull
    @ManyToOne
    private BuildingType buildingType;

    @OneToMany(mappedBy = "building", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Review> reviews = Collections.emptyList();
    @Builder.Default
    private Boolean deleted = false;

}
