package com.imaee.propinq.rents.services.usecases.interfaces;

import com.imaee.propinq.rents.controllers.requests.CancelRentRequest;

import java.util.UUID;

public interface ICancelRentUseCase {
    void cancelRent(UUID rentId, CancelRentRequest cancelRentRequest);
}