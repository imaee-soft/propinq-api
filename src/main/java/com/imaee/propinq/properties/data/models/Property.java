package com.imaee.propinq.properties.data.models;

import com.imaee.propinq.buildings.data.models.Building;
import com.imaee.propinq.shared.data.models.Review;
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
    @OneToMany(cascade = CascadeType.ALL)
    private List<Image> images = Collections.emptyList();

    @NonNull
    @ManyToOne
    private PropertyType propertyType;

    private Boolean deleted = false;
}
