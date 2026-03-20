package com.imaee.propinq.shared.data.models;

import com.imaee.propinq.buildings.data.models.Building;
import com.imaee.propinq.properties.data.models.Property;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.imaee.propinq.users.data.models.User;

import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Review {

    @Id
    private final UUID reviewId = UUID.randomUUID();

    @NotNull
    private String content;

    @NotNull
    private Integer rating;

    @NotNull
    @ManyToOne
    private User author;

    @ManyToOne
    private Building building;

    @ManyToOne
    private Property property;
}
