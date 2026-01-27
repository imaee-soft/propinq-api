package com.imaee.propinq.favorites.data.models;

import com.imaee.propinq.buildings.data.models.Building;
import com.imaee.propinq.properties.data.models.Property;
import com.imaee.propinq.users.data.models.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "favorites")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Favorite {

    @Id
    private final UUID favoriteId = java.util.UUID.randomUUID();

    @ManyToOne(optional = false)
    @JoinColumn(name = "userId")
    private User userID;

    @ManyToOne(optional = true)
    @JoinColumn(name = "propertyId")
    private Property propertyID;

    @ManyToOne(optional = true)
    @JoinColumn(name = "builderId")
    private Building buildingID;

    @Builder.Default
    private LocalDateTime favoriteDate = LocalDateTime.now();
}