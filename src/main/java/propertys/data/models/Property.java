package propertys.data.models;

import buildings.data.models.Building;
import jakarta.persistence.*;
import lombok.*;
import shared.data.models.Locatable;


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
    private Double latitude;

    @NonNull
    private Double longitude;

    @ManyToOne
    private Building building;

}
