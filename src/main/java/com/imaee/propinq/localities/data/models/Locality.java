package com.imaee.propinq.localities.data.models;


import com.imaee.propinq.neighborhoods.data.models.Neighborhood;
import com.imaee.propinq.provinces.data.models.Province;
import com.imaee.propinq.shared.data.models.Locatable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Locality extends Locatable {
    @Id
    private UUID localityId = UUID.randomUUID();

    @NotNull
    private String name;

    @ManyToOne
    @JoinColumn(name = "province_id")
    private Province province;

    @OneToMany
    private List<Neighborhood> neighborhoods;

    @NotNull
    @Builder.Default
    private boolean deleted = false;
}
