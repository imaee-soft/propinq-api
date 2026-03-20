package com.imaee.propinq.poi.data.models;

import com.imaee.propinq.poi.data.enums.PoiType;
import com.imaee.propinq.shared.data.models.Locatable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity(name = "Poi")
@Table(name = "poi")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Poi extends Locatable {
    @Id
    @Column(name = "id", length = 100)
    @Builder.Default
    private UUID poiId = UUID.randomUUID();

    @NotNull
    @Enumerated(EnumType.STRING)
    private PoiType type;

    @NotNull
    private String name;

}