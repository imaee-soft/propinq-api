package com.imaee.propinq.rents.data.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name="rent_documents")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Document {

    @Id
    private final UUID documentId = UUID.randomUUID();

    @ManyToOne
    @NotNull
    private Rent rent;

    private String name;

    @Lob
    @NotNull
    private byte[] content;

    @NotNull
    private LocalDateTime createdAt = LocalDateTime.now();
}