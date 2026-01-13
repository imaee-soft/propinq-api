package com.imaee.propinq.rents.services.facade;

import com.imaee.propinq.rents.data.models.Contact;

public interface IContactFacade {
    void throwExceptionIfContactSubjectIsNotLoggedUser(Contact contact);
}