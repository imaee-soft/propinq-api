package com.imaee.propinq.buildings.services.usecases.interfaces;

import java.util.UUID;

public interface IExistsApartmentNumberUseCase {
    boolean existsApartmentNumber(UUID buildingId, String number);
}