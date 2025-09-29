package com.imaee.propinq.neighborhoods.data.models;

import com.imaee.propinq.localities.data.models.Locality;
import com.imaee.propinq.shared.data.models.Locatable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Neighborhood extends Locatable {
    @Id
    private UUID neighborhoodId = UUID.randomUUID();

    @NotNull
    private String name;

    @ManyToOne
    @JoinColumn(name = "locality_id")
    private Locality locality;

    @NotNull
    @Builder.Default
    private boolean deleted = false;
    
}
