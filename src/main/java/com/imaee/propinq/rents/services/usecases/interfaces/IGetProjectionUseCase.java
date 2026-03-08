package com.imaee.propinq.rents.services.usecases.interfaces;

import com.imaee.propinq.projections.responses.Projection;

import java.util.List;
import java.util.UUID;

public interface IGetProjectionUseCase {
    List<Projection> getRentProjection(UUID rentId);
}