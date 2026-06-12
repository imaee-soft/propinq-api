package com.imaee.propinq.users.services.usecases.interfaces;

public interface IActivateUserUseCase {
    void activateUser(String email, String verificationCode);
}