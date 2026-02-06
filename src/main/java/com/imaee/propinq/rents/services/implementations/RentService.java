package com.imaee.propinq.rents.services.implementations;

import com.imaee.propinq.rents.controllers.requests.RentRequest;
import com.imaee.propinq.rents.controllers.responses.RentDetail;
import com.imaee.propinq.rents.controllers.responses.SaveRentResponse;
import com.imaee.propinq.rents.controllers.responses.SimpleRent;
import com.imaee.propinq.rents.services.interfaces.IRentService;
import com.imaee.propinq.rents.services.usecases.interfaces.IGetOwnerRentsUseCase;
import com.imaee.propinq.rents.services.usecases.interfaces.IGetRentDetailUseCase;
import com.imaee.propinq.rents.services.usecases.interfaces.ISaveRentUseCase;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@AllArgsConstructor
public class RentService implements IRentService {

    private final ISaveRentUseCase saveRentUseCase;
    private final IGetOwnerRentsUseCase getOwnerRentsUseCase;
    private final IGetRentDetailUseCase getRentDetailUseCase;

    @Override
    public SaveRentResponse saveRent(RentRequest rentRequest, MultipartFile contract) {
        return saveRentUseCase.saveRent(rentRequest, contract);
    }

    @Override
    public Page<SimpleRent> getOwnerRents(Integer pageNumber, Integer pageSize) {
        return getOwnerRentsUseCase.getOwnerRents(pageNumber, pageSize);
    }

    @Override
    public RentDetail getRent(UUID rentId) {
        return getRentDetailUseCase.getRent(rentId);
    }
}