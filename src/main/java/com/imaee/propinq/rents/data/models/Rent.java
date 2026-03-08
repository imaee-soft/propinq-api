package com.imaee.propinq.rents.data.models;

import com.imaee.propinq.contacts.data.models.Contact;
import com.imaee.propinq.rents.data.enums.RaiseIndex;
import com.imaee.propinq.rents.data.enums.RentState;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.EnumType.STRING;
import static java.util.Collections.emptyList;

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

    @OneToMany(mappedBy = "rent", cascade = ALL)
    private List<Document> documents = emptyList();

    @NotNull
    private LocalDate rentDate;

    @NotNull
    private LocalDate rentDueDate;

    @NotNull
    @Enumerated(STRING)
    private RentState rentState;

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

    private String cancellationReason;
    private LocalDate cancellationDate;
}