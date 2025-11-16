package com.imaee.propinq.rents.data.models;

import com.imaee.propinq.properties.data.models.Property;
import com.imaee.propinq.rents.data.enums.ContactState;
import com.imaee.propinq.users.data.models.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.imaee.propinq.rents.data.enums.ContactState.ACCEPTED;
import static com.imaee.propinq.rents.data.enums.ContactState.CREATED;

@Entity(name="contacts")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Contact {

    @Id
    private final UUID contactId = UUID.randomUUID();

    @ManyToOne
    @NotNull
    private User issuer;

    @ManyToOne
    @NotNull
    private Property property;

    @NotNull
    private String contactMessage;

    private String contactAnswer;
    private LocalDateTime answerDate;

    @Builder.Default
    private LocalDateTime issueDate = LocalDateTime.now();

    @Builder.Default
    private ContactState state = CREATED;

    public boolean isAccepted() {
        return ACCEPTED.equals(state);
    }
}