package com.imaee.propinq.rents.services.usecases.implementations;

import com.imaee.propinq.auth.services.interfaces.IAuthenticatedUserService;
import com.imaee.propinq.contacts.services.usecases.interfaces.IFindContactByIdUseCase;
import com.imaee.propinq.contacts.services.usecases.interfaces.IRentContactUseCase;
import com.imaee.propinq.rents.controllers.requests.RentRequest;
import com.imaee.propinq.rents.data.repositories.IRentRepository;
import com.imaee.propinq.rents.mappers.RentMapper;
import com.imaee.propinq.rents.services.usecases.interfaces.ISaveRentUseCase;
import com.imaee.propinq.users.data.models.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;

@Component
@AllArgsConstructor
public class SaveRentUseCase implements ISaveRentUseCase {

    private final IFindContactByIdUseCase findContactByIdUseCase;
    private final IAuthenticatedUserService authenticatedUserService;
    private final IRentRepository rentRepository;
    private final IRentContactUseCase rentContactUseCase;

    @Override
    public void saveRent(RentRequest rentRequest, MultipartFile contract) {
        final var contact = findContactByIdUseCase.findContactById(rentRequest.contactId());
        throwExceptionIfLoggedUserIsNotOwner(contact.getProperty().getUser());
        final var contractPdf = getBytes(contract);
        rentRepository.save(RentMapper.buildRent(
                contact,
                rentRequest,
                contractPdf
        ));
        rentContactUseCase.markAsRented(contact);
    }

    private void throwExceptionIfLoggedUserIsNotOwner(User owner) {
        final var loggedUser = authenticatedUserService.getLoggedUserOrThrowException();
        if (!owner.getUserId().equals(loggedUser.getUserId()))
            throw new ResponseStatusException(FORBIDDEN);
    }

    private byte[] getBytes(MultipartFile contract) {
        try { return contract.getBytes(); }
        catch (IOException ex) { throw new ResponseStatusException(BAD_REQUEST, ex.getMessage()); }
    }
}