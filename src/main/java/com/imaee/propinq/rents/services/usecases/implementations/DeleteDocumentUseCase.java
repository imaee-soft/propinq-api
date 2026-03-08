package com.imaee.propinq.rents.services.usecases.implementations;

import com.imaee.propinq.auth.services.interfaces.IAuthenticatedUserService;
import com.imaee.propinq.rents.data.models.Rent;
import com.imaee.propinq.rents.data.repositories.IDocumentRepository;
import com.imaee.propinq.rents.services.usecases.interfaces.IDeleteDocumentUseCase;
import com.imaee.propinq.users.data.models.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Component
@AllArgsConstructor
public class DeleteDocumentUseCase implements IDeleteDocumentUseCase {

    private final IAuthenticatedUserService authenticatedUserService;
    private final IDocumentRepository documentRepository;

    @Override
    public void deleteDocument(UUID documentId) {
        final var document = documentRepository.findById(documentId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
        final var rent = document.getRent();
        final var loggedUser = authenticatedUserService.getLoggedUserOrThrowException();
        throwExceptionIfIsNotOwner(rent, loggedUser);
        documentRepository.delete(document);
    }

    private void throwExceptionIfIsNotOwner(Rent rent, User user) {
        if (!rent.getContact().getProperty().getUser().getUserId().equals(user.getUserId()))
            throw new ResponseStatusException(FORBIDDEN);
    }
}
