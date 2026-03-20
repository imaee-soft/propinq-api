package com.imaee.propinq.auth.services.interfaces;

import com.imaee.propinq.users.data.models.User;

public interface IAuthenticatedUserService {
    User safelyGetLoggedUser();
    User getLoggedUserOrThrowException();
}