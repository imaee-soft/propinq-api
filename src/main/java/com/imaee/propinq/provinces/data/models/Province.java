package com.imaee.propinq.provinces.data.models;

import com.imaee.propinq.localities.data.models.Locality;
import com.imaee.propinq.shared.data.models.Locatable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
public class Province extends Locatable {
    @Id
    private UUID provinceId = UUID.randomUUID();

    @NotNull
    private String name;

    @OneToMany
    private List<Locality> localities;
}
