package com.imaee.propinq.users.services.usecases.interfaces;

import com.imaee.propinq.users.controllers.requests.RecoverPasswordRequest;

public interface IRecoverPasswordUseCase {
    void sendEmailToRecoverPassword(String email);
    void recoverPassword(RecoverPasswordRequest recoverPasswordRequest);
}
