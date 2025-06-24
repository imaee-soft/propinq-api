package com.imaee.propinq.neighborhood.data.models;

import com.imaee.propinq.shared.data.models.Locatable;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Table(uniqueConstraints = {
        @UniqueConstraint(
                name = "UniqueNameAndLocality",
                columnNames = {
                        "name",
                        "locality"
                }
        )
})
public class Neighborhood extends Locatable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NonNull
    private String name;

    @NonNull
    @ManyToOne
    private Locality locality;
}
