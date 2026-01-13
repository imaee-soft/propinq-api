package com.imaee.propinq.rents.services.usecases.interfaces;

import com.imaee.propinq.rents.controllers.responses.ContactDetailResponse;
import org.springframework.data.domain.Page;

public interface IGetTenantContactsUseCase {
    Page<ContactDetailResponse> getTenantContacts(Integer pageNumber, Integer pageSize);
}