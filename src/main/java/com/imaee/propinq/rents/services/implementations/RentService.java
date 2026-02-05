package com.imaee.propinq.rents.services.implementations;

import com.imaee.propinq.rents.controllers.requests.RentRequest;
import com.imaee.propinq.rents.controllers.responses.SimpleRent;
import com.imaee.propinq.rents.services.interfaces.IRentService;
import com.imaee.propinq.rents.services.usecases.interfaces.IGetOwnerRentsUseCase;
import com.imaee.propinq.rents.services.usecases.interfaces.ISaveRentUseCase;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@AllArgsConstructor
public class RentService implements IRentService {

    private final ISaveRentUseCase saveRentUseCase;
    private final IGetOwnerRentsUseCase getOwnerRentsUseCase;

    @Override
    public void saveRent(RentRequest rentRequest, MultipartFile contract) {
        saveRentUseCase.saveRent(rentRequest, contract);
    }

    @Override
    public Page<SimpleRent> getOwnerRents(Integer pageNumber, Integer pageSize) {
        return getOwnerRentsUseCase.getOwnerRents(pageNumber, pageSize);
    }
}