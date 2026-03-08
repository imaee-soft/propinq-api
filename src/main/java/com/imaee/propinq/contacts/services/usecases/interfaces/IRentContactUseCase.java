package com.imaee.propinq.contacts.services.usecases.interfaces;

import com.imaee.propinq.contacts.data.models.Contact;

public interface IRentContactUseCase {
    void markAsRented(Contact contact);
}