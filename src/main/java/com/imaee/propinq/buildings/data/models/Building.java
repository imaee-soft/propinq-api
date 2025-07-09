package com.imaee.propinq.buildings.data.models;

import com.imaee.propinq.buildings.data.enums.BuildingType;
import com.imaee.propinq.properties.data.models.Property;
import com.imaee.propinq.shared.data.models.Image;
import com.imaee.propinq.shared.data.models.Locatable;
import com.imaee.propinq.users.data.models.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.imaee.propinq.buildings.data.enums.BuildingType.EDIFICIO;
import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.EnumType.STRING;
import static java.util.UUID.randomUUID;
import static lombok.AccessLevel.PROTECTED;

@Entity(name = "buildings")
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@SuperBuilder
@Data
public class Building extends Locatable {

    @Id
    private final UUID buildingId = randomUUID();

    @NonNull
    @Column(unique = true)
    private String name;

    @NonNull
    private String description;

    @NonNull
    private String address;

    @NonNull
    @OneToMany(cascade = ALL)
    private List<Image> images = new ArrayList<>();

    @OneToMany(mappedBy = "building", cascade = ALL)
    private List<Property> properties = new ArrayList<>();

    @NonNull
    @ManyToOne
    private User user;

    @Enumerated(STRING)
    private BuildingType buildingType = EDIFICIO;

    private boolean deleted = false;
}