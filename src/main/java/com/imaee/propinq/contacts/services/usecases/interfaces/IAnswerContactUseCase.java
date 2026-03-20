package com.imaee.propinq.contacts.services.usecases.interfaces;

import com.imaee.propinq.contacts.controllers.requests.AnswerContactRequest;

import java.util.UUID;

public interface IAnswerContactUseCase {
    void answerContactRequest(UUID contactId, AnswerContactRequest answerContactRequest);
}