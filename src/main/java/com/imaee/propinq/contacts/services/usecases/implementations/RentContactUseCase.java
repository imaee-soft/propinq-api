package com.imaee.propinq.contacts.services.usecases.implementations;

import com.imaee.propinq.contacts.data.models.Contact;
import com.imaee.propinq.contacts.data.repositories.IContactRepository;
import com.imaee.propinq.contacts.services.usecases.interfaces.IRentContactUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import static com.imaee.propinq.contacts.data.enums.ContactState.RENTED;

@Component
@AllArgsConstructor
public class RentContactUseCase implements IRentContactUseCase {

    private final IContactRepository contactRepository;

    @Override
    public void markAsRented(Contact contact) {
        contact.setState(RENTED);
        contactRepository.save(contact);
    }
}