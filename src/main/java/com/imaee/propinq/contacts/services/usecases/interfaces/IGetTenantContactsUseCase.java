package com.imaee.propinq.contacts.services.usecases.interfaces;

import com.imaee.propinq.contacts.controllers.responses.ContactDetailResponse;
import org.springframework.data.domain.Page;

public interface IGetTenantContactsUseCase {
    Page<ContactDetailResponse> getTenantContacts(Integer pageNumber, Integer pageSize);
}