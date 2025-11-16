package com.imaee.propinq.rents.services.usecases.implementations;

import com.imaee.propinq.rents.data.models.Contact;
import com.imaee.propinq.rents.data.repositories.IContactRepository;
import com.imaee.propinq.rents.services.usecases.interfaces.IFindContactByIdUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

import static com.imaee.propinq.rents.Constants.CONTACT_NOT_FOUND;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Component
@AllArgsConstructor
public class FindContactByIdUseCase implements IFindContactByIdUseCase {

    private final IContactRepository contactRepository;

    @Override
    public Contact findContactById(UUID contactId) {
        return contactRepository.findById(contactId)
                .orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, CONTACT_NOT_FOUND));
    }
}