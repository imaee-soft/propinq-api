package com.imaee.propinq.users.services.usecases.interfaces;

import com.imaee.propinq.auth.controllers.requests.SignUpRequest;

public interface ISaveUserUseCase {
    void saveUser(SignUpRequest createUserRequest);
}
