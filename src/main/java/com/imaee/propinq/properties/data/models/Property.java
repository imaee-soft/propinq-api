package com.imaee.propinq.properties.data.models;

import com.imaee.propinq.buildings.data.models.Building;
import com.imaee.propinq.users.data.models.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import com.imaee.propinq.shared.data.models.Image;
import com.imaee.propinq.shared.data.models.Locatable;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static jakarta.persistence.FetchType.EAGER;

@Entity(name="properties")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Property extends Locatable {

    @Id
    private final UUID propertyId = UUID.randomUUID();

    @NotNull
    private String address;

    @ManyToOne
    private Building building;

    @NotNull
    @OneToMany(cascade = CascadeType.ALL, fetch = EAGER)
    private List<Image> images = Collections.emptyList();

    @NotNull
    @ManyToOne
    private PropertyType propertyType;

    @NotNull
    private Double price;

    @NotNull
    private String description;

    @NotNull
    private String title;

    private Integer floor;

    @NotNull
    private Integer bedrooms;

    @NotNull
    private Integer bathrooms;

    @NotNull
    private boolean petsAllowed = false;

    @NotNull
    private Double area;

    private String apartmentNumber;

    @NotNull
    @ManyToOne
    private User user;

    @NotNull
    @Builder.Default
    private boolean deleted = false;

}
