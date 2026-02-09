package com.imaee.propinq.rents.services.usecases.implementations;

import com.imaee.propinq.auth.services.interfaces.IAuthenticatedUserService;
import com.imaee.propinq.rents.controllers.requests.RentDocumentRequest;
import com.imaee.propinq.rents.data.models.Rent;
import com.imaee.propinq.rents.data.repositories.IDocumentRepository;
import com.imaee.propinq.rents.services.usecases.interfaces.IFindRentByIdUseCase;
import com.imaee.propinq.rents.services.usecases.interfaces.ISaveDocumentUseCase;
import com.imaee.propinq.users.data.models.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

import static com.imaee.propinq.rents.mappers.RentMapper.buildDocument;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;

@Component
@AllArgsConstructor
public class SaveDocumentUseCase implements ISaveDocumentUseCase {

    private final IFindRentByIdUseCase findRentByIdUseCase;
    private final IAuthenticatedUserService authenticatedUserService;
    private final IDocumentRepository documentRepository;

    @Override
    public void saveDocument(RentDocumentRequest rentDocumentRequest, MultipartFile document) {
        final var rent = findRentByIdUseCase.findById(rentDocumentRequest.rentId());
        final var loggedUser = authenticatedUserService.getLoggedUserOrThrowException();
        throwExceptionIfIsNotOwner(rent, loggedUser);
        saveDocument(rentDocumentRequest, document, rent);
    }

    private void throwExceptionIfIsNotOwner(Rent rent, User user) throws ResponseStatusException {
        final var contact = rent.getContact();
        if (!user.getUserId().equals(contact.getProperty().getUser().getUserId()))
            throw new ResponseStatusException(FORBIDDEN);
    }

    private void saveDocument(RentDocumentRequest rentDocumentRequest, MultipartFile document, Rent rent) {
        final var content = getBytes(document);
        documentRepository.save(buildDocument(rentDocumentRequest, content, rent));
    }

    private byte[] getBytes(MultipartFile contract) {
        try { return contract.getBytes(); }
        catch (IOException ex) { throw new ResponseStatusException(BAD_REQUEST, ex.getMessage()); }
    }
}