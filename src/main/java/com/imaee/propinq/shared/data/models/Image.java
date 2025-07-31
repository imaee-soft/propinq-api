package com.imaee.propinq.shared.data.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity(name="images")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Image {

    @Id
    private String url;

    @NonNull
    private String fileName;

    private boolean deleted = false;

    @Column(nullable = false, unique = true)
    private String publicId;
}