package buildings.data.models;

import shared.data.models.Image;
import propertys.data.models.Property;
import jakarta.persistence.*;
import lombok.*;
import shared.data.models.Locatable;

import java.util.List;
import java.util.UUID;

@Entity(name="buildings")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Building extends Locatable {

    @Id
    private UUID buildingId = UUID.randomUUID();

    @NonNull
    private Double latitude;

    @NonNull
    private Double longitude;

    @NonNull
    private String name;

    @NonNull
    private String description;

    @NonNull
    private String address;

    @ManyToMany @NonNull
    private List<Image> images;

    @OneToMany(mappedBy = "building", cascade = CascadeType.ALL)
    private List<Property> properties;

    private User user;

}
