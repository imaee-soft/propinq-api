package com.imaee.propinq.contacts.services.usecases.implementations;

import com.imaee.propinq.contacts.controllers.responses.ContactDetailResponse;
import com.imaee.propinq.contacts.data.repositories.IContactRepository;
import com.imaee.propinq.contacts.mappers.ContactMapper;
import com.imaee.propinq.contacts.services.usecases.interfaces.IGetTenantContactsUseCase;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class GetTenantContactsUseCase implements IGetTenantContactsUseCase {

    private final IContactRepository contactRepository;

    @Override
    public Page<ContactDetailResponse> getTenantContacts(Integer pageNumber, Integer pageSize) {
        final var loggedUserEmail = (String) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        return contactRepository.findAllByIssuer_Email(
                loggedUserEmail,
                PageRequest.of(pageNumber, pageSize)
        ).map(ContactMapper::buildContactDetailResponse);
    }
}