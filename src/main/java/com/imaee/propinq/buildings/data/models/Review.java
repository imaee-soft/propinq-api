package com.imaee.propinq.buildings.data.models;

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

}
