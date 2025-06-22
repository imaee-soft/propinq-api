package com.imaee.propinq.shared.data.models;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import com.imaee.propinq.users.data.models.User;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

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

    private Boolean deleted = false;
}
