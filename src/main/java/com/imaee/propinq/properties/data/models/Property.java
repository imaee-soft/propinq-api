package com.imaee.propinq.properties.data.models;

import com.imaee.propinq.buildings.data.models.Building;
import com.imaee.propinq.users.data.models.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import com.imaee.propinq.shared.data.models.Image;
import com.imaee.propinq.shared.data.models.Locatable;
import org.springframework.data.repository.cdi.Eager;

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

    @NonNull
    private String address;

    @ManyToOne
    private Building building;

    @NonNull
    @OneToMany(cascade = CascadeType.ALL, fetch = EAGER)
    private List<Image> images = Collections.emptyList();

    @NonNull
    @ManyToOne
    private PropertyType propertyType;

    @NonNull
    private Double price;

    @NonNull
    private String description;

    @NonNull
    private String title;

    private Integer floor;

    @NonNull
    private Integer bedrooms;

    @NonNull
    private Integer bathrooms;

    @NonNull
    private boolean petsAllowed = false;

    @NonNull
    private Double area;

    private String apartmentNumber;

    @NonNull
    @ManyToOne
    private User user;

    private boolean deleted = false;

}
