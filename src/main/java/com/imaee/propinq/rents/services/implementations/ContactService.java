package com.imaee.propinq.rents.services.implementations;

import com.imaee.propinq.rents.controllers.requests.AnswerContactRequest;
import com.imaee.propinq.rents.controllers.requests.ContactRequest;
import com.imaee.propinq.rents.controllers.responses.ContactResponse;
import com.imaee.propinq.rents.services.interfaces.IContactService;
import com.imaee.propinq.rents.services.usecases.interfaces.IAnswerContactUseCase;
import com.imaee.propinq.rents.services.usecases.interfaces.IGetContactUseCase;
import com.imaee.propinq.rents.services.usecases.interfaces.ISaveContactUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class ContactService implements IContactService {

    private final ISaveContactUseCase saveContactUseCase;
    private final IGetContactUseCase getContactUseCase;
    private final IAnswerContactUseCase answerContactUseCase;

    @Override
    public void saveContactRequest(ContactRequest contactRequest) {
        saveContactUseCase.saveContactRequest(contactRequest);
    }

    @Override
    public ContactResponse getContactRequest(UUID contactId) {
        return getContactUseCase.getContactRequest(contactId);
    }

    @Override
    public void answerContactRequest(UUID contactId, AnswerContactRequest answerContactRequest) {
        answerContactUseCase.answerContactRequest(contactId, answerContactRequest);
    }
}