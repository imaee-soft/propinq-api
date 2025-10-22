package com.imaee.propinq.neighborhoods.services.usecases.interfaces;

import com.imaee.propinq.neighborhoods.controllers.responses.NeighborhoodResponse;

import java.util.List;

public interface IGetNeighborhoodsUseCase {
    List<NeighborhoodResponse> getNeighborhoods();
}
