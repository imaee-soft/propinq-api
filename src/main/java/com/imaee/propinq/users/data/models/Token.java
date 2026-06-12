package com.imaee.propinq.users.data.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

import static java.time.LocalDateTime.now;

@Entity(name = "tokens")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Token {

    @Id
    private final UUID tokenId = UUID.randomUUID();

    @Builder.Default
    private String verificationCode = String.format("%06d", new java.util.Random().nextInt(1000000));

    @Builder.Default
    private LocalDateTime tokenExpirationDate = now().plusMinutes(1440);

    @ManyToOne
    private User user;

    public boolean isExpired() {
        return tokenExpirationDate.isBefore(now());
    }
}