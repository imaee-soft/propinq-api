package com.imaee.propinq.rents.services.usecases.implementations;

import com.imaee.propinq.rents.data.models.Rent;
import com.imaee.propinq.rents.data.repositories.IRentRepository;
import com.imaee.propinq.rents.services.usecases.interfaces.IFindRentByIdUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Component
@AllArgsConstructor
public class FindRentByIdUseCase implements IFindRentByIdUseCase {

    private static final String NOT_FOUND = "No se encontró el alquiler solicitado";
    private final IRentRepository rentRepository;

    @Override
    public Rent findById(UUID rentId) {
        return rentRepository.findById(rentId)
                .orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, NOT_FOUND));
    }
}
