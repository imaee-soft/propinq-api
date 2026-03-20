package com.imaee.propinq.projections.services;

import com.imaee.propinq.projections.responses.ProjectionWrapper;
import com.imaee.propinq.rents.data.enums.RaiseIndex;

import java.time.LocalDate;

public interface IPriceProjectionService {
    ProjectionWrapper calculateProjection(
            Double price,
            LocalDate initialDate,
            Integer months,
            RaiseIndex index
    );
}