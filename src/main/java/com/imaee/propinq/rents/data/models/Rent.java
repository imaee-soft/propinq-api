package com.imaee.propinq.rents.data.models;

import com.imaee.propinq.contacts.data.models.Contact;
import com.imaee.propinq.rents.data.enums.RaiseIndex;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity(name="rents")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Rent {

    @Id
    private final UUID rentId = UUID.randomUUID();

    @ManyToOne
    @NotNull
    private Contact contact;

    @NotNull
    private LocalDate rentDate;

    @NotNull
    private Integer rentDuration;

    @NotNull
    private Integer payday;

    @NotNull
    private Double rentPrice;

    @NotNull
    private RaiseIndex raiseIndex;

    @NotNull
    private Integer raiseMonths;

    @Lob
    @NotNull
    private byte[] contract;
}