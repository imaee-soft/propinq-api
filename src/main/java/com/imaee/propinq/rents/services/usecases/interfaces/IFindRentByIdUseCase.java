package com.imaee.propinq.rents.services.usecases.interfaces;

import com.imaee.propinq.rents.data.models.Rent;

import java.util.UUID;

public interface IFindRentByIdUseCase {
    Rent findById(UUID rentId);
}