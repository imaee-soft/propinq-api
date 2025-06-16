package com.imaee.propinq.neighborhood.data.models;

import com.imaee.propinq.shared.data.models.Locatable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Locality extends Locatable {

    @Id
    private UUID id = UUID.randomUUID();

    @NonNull
    private String name;

}
