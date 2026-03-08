package com.imaee.propinq.rents.services.usecases.interfaces;

import com.imaee.propinq.rents.controllers.responses.RentDetail;

import java.util.UUID;

public interface IGetRentDetailUseCase {
    RentDetail getRent(UUID rentId);
}