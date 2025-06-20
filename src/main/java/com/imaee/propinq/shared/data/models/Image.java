package com.imaee.propinq.shared.data.models;

import com.imaee.propinq.buildings.data.models.Building;
import com.imaee.propinq.properties.data.models.Property;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.*;

import java.util.List;

@Entity(name="images")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Image {

    @Id
    private String url;

    @NonNull
    private String name;

    @ManyToMany
    private List<Building> building;

    @ManyToMany
    private List<Property> properties;

}
