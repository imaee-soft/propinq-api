package com.imaee.propinq.shared.data.models;

import com.imaee.propinq.buildings.data.models.Building;
import com.imaee.propinq.properties.data.models.Property;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;
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

    @NonNull
    private String content;

    @NonNull
    private Integer rating;

    @NonNull
    @ManyToOne
    private User author;

    @ManyToOne
    private Building building;

    @ManyToOne
    private Property property;
}
