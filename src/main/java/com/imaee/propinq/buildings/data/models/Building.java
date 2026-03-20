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
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.imaee.propinq.buildings.data.enums.BuildingType.EDIFICIO;
import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.EAGER;
import static java.util.UUID.randomUUID;
import static lombok.AccessLevel.PROTECTED;

@EqualsAndHashCode(callSuper = true)
@Entity(name = "buildings")
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@SuperBuilder
@Data
public class Building extends Locatable {

    @Id
    private final UUID buildingId = randomUUID();

    @NotNull
    @Column(unique = true)
    private String name;

    @NotNull
    private String description;

    @NotNull
    private String address;

    @NotNull
    @OneToMany(cascade = ALL, fetch = EAGER)
    private List<Image> images;

    @OneToMany(mappedBy = "building", cascade = ALL)
    private List<Property> properties = new ArrayList<>();

    @NotNull
    @ManyToOne
    private User user;

    @Enumerated(STRING)
    private BuildingType buildingType = EDIFICIO;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    private boolean deleted = false;
}