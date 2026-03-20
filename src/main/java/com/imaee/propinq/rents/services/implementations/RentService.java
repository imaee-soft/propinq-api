package com.imaee.propinq.rents.services.implementations;

import com.imaee.propinq.projections.responses.Projection;
import com.imaee.propinq.rents.controllers.requests.CancelRentRequest;
import com.imaee.propinq.rents.controllers.requests.RentDocumentRequest;
import com.imaee.propinq.rents.controllers.requests.RentRequest;
import com.imaee.propinq.rents.controllers.responses.RentDetail;
import com.imaee.propinq.rents.controllers.responses.SaveRentResponse;
import com.imaee.propinq.rents.controllers.responses.SimpleRent;
import com.imaee.propinq.rents.services.interfaces.IRentService;
import com.imaee.propinq.rents.services.usecases.interfaces.*;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RentService implements IRentService {

    private final ISaveRentUseCase saveRentUseCase;
    private final IGetOwnerRentsUseCase getOwnerRentsUseCase;
    private final IGetTenantRentsUseCase getTenantRentsUseCase;
    private final IGetRentDetailUseCase getRentDetailUseCase;
    private final IGetProjectionUseCase getProjectionUseCase;
    private final ISaveDocumentUseCase saveDocumentUseCase;
    private final ICancelRentUseCase cancelRentUseCase;
    private final IUpdateContractUseCase updateContractUseCase;
    private final IDeleteDocumentUseCase deleteDocumentUseCase;

    @Override
    public SaveRentResponse saveRent(RentRequest rentRequest, MultipartFile contract) {
        return saveRentUseCase.saveRent(rentRequest, contract);
    }

    @Override
    public Page<SimpleRent> getOwnerRents(Integer pageNumber, Integer pageSize, String surname, String status) {
        return getOwnerRentsUseCase.getOwnerRents(pageNumber, pageSize, surname, status);
    }

    @Override
    public Page<SimpleRent> getTenantRents(Integer pageNumber, Integer pageSize) {
        return getTenantRentsUseCase.getTenantRents(pageNumber, pageSize);
    }

    @Override
    public RentDetail getRent(UUID rentId) {
        return getRentDetailUseCase.getRent(rentId);
    }

    @Override
    public List<Projection> getRentProjection(UUID rentId) {
        return getProjectionUseCase.getRentProjection(rentId);
    }

    @Override
    public void saveDocument(RentDocumentRequest rentDocumentRequest, MultipartFile document) {
        saveDocumentUseCase.saveDocument(rentDocumentRequest, document);
    }

    @Override
    public void cancelRent(UUID rentId, CancelRentRequest cancelRentRequest) {
        cancelRentUseCase.cancelRent(rentId, cancelRentRequest);
    }

    @Override
    public void updateContract(UUID rentId, MultipartFile contract) {
        updateContractUseCase.updateContract(rentId, contract);
    }

    @Override
    public void deleteDocument(UUID documentId) {
        deleteDocumentUseCase.deleteDocument(documentId);
    }
}