package com.imaee.propinq.contacts.services.usecases.interfaces;

import com.imaee.propinq.contacts.controllers.responses.ContactDetailResponse;
import org.springframework.data.domain.Page;

public interface IGetOwnerContactsUseCase {
    Page<ContactDetailResponse> getOwnerContacts(Integer pageNumber, Integer pageSize, String surname, String status);
}