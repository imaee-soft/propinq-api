package com.imaee.propinq.contacts.services.facade;

import com.imaee.propinq.contacts.data.models.Contact;
import com.imaee.propinq.users.data.models.User;

public interface IContactFacade {
    void throwExceptionIfContactSubjectIsNotLoggedUser(Contact contact);
    void throwExceptionIfContactSubjectIsNotLoggedUser(Contact contact, User user);
}