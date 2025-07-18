package com.imaee.propinq.buildings.services.usecases.interfaces;

import com.imaee.propinq.buildings.data.models.Building;

import java.util.UUID;

public interface IFindBuildingByIdUseCase {
    Building findBuilding(UUID buildingId);
}