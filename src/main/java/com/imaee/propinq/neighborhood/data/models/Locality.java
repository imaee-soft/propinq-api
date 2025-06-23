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
public class Locality extends Locatable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NonNull
    @Column(unique = true)
    private String name;
}
