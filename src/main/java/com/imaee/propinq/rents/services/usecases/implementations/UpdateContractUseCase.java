package com.imaee.propinq.rents.services.usecases.implementations;

import com.imaee.propinq.auth.services.interfaces.IAuthenticatedUserService;
import com.imaee.propinq.rents.data.models.Rent;
import com.imaee.propinq.rents.data.repositories.IRentRepository;
import com.imaee.propinq.rents.services.usecases.interfaces.IFindRentByIdUseCase;
import com.imaee.propinq.rents.services.usecases.interfaces.IUpdateContractUseCase;
import com.imaee.propinq.users.data.models.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.UUID;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;

@Component
@AllArgsConstructor
public class UpdateContractUseCase implements IUpdateContractUseCase {

    private final IFindRentByIdUseCase findRentByIdUseCase;
    private final IAuthenticatedUserService authenticatedUserService;
    private final IRentRepository rentRepository;

    @Override
    public void updateContract(UUID rentId, MultipartFile contract) {
        final var rent = findRentByIdUseCase.findById(rentId);
        final var loggedUser = authenticatedUserService.getLoggedUserOrThrowException();
        throwExceptionIfIsNotOwner(rent, loggedUser);
        
        final var contractBytes = getBytes(contract);
        rent.setContract(contractBytes);
        rentRepository.save(rent);
    }

    private void throwExceptionIfIsNotOwner(Rent rent, User user) throws ResponseStatusException {
        final var contact = rent.getContact();
        if (!user.getUserId().equals(contact.getProperty().getUser().getUserId()))
            throw new ResponseStatusException(FORBIDDEN);
    }

    private byte[] getBytes(MultipartFile contract) {
        try { return contract.getBytes(); }
        catch (IOException ex) { throw new ResponseStatusException(BAD_REQUEST, ex.getMessage()); }
    }
}
