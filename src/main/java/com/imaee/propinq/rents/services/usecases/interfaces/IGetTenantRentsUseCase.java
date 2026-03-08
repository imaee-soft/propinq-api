package com.imaee.propinq.rents.services.usecases.interfaces;

import com.imaee.propinq.rents.controllers.responses.SimpleRent;
import org.springframework.data.domain.Page;

public interface IGetTenantRentsUseCase {
    Page<SimpleRent> getTenantRents(Integer pageNumber, Integer pageSize);
}