package com.imaee.propinq.rents.services.usecases.interfaces;

import com.imaee.propinq.rents.controllers.requests.AnswerContactRequest;

import java.util.UUID;

public interface IAnswerContactUseCase {
    void answerContactRequest(UUID contactId, AnswerContactRequest answerContactRequest);
}