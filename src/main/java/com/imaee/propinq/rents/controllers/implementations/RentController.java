package com.imaee.propinq.rents.controllers.implementations;

import com.imaee.propinq.projections.responses.Projection;
import com.imaee.propinq.rents.controllers.interfaces.IRentController;
import com.imaee.propinq.rents.controllers.requests.CancelRentRequest;
import com.imaee.propinq.rents.controllers.requests.RentDocumentRequest;
import com.imaee.propinq.rents.controllers.requests.RentRequest;
import com.imaee.propinq.rents.controllers.responses.RentDetail;
import com.imaee.propinq.rents.controllers.responses.SaveRentResponse;
import com.imaee.propinq.rents.controllers.responses.SimpleRent;
import com.imaee.propinq.rents.services.interfaces.IRentService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
public class RentController implements IRentController {

    private final IRentService rentService;

    @Override
    public SaveRentResponse saveRent(RentRequest rentRequest, MultipartFile contract) {
        return rentService.saveRent(rentRequest, contract);
    }

    @Override
    public Page<SimpleRent> getOwnerRents(Integer pageNumber, Integer pageSize, String surname, String status) {
        return rentService.getOwnerRents(pageNumber, pageSize, surname, status);
    }

    @Override
    public Page<SimpleRent> getTenantRents(Integer pageNumber, Integer pageSize) {
        return rentService.getTenantRents(pageNumber, pageSize);
    }

    @Override
    public RentDetail getRent(UUID rentId) {
        return rentService.getRent(rentId);
    }

    @Override
    public List<Projection> getRentProjection(UUID rentId) {
        return rentService.getRentProjection(rentId);
    }

    @Override
    public void saveDocument(RentDocumentRequest rentDocumentRequest, MultipartFile document) {
        rentService.saveDocument(rentDocumentRequest, document);
    }

    @Override
    public void cancelRent(UUID rentId, CancelRentRequest cancelRentRequest) {
        rentService.cancelRent(rentId, cancelRentRequest);
    }

    @Override
    public void updateContract(UUID rentId, MultipartFile contract) {
        rentService.updateContract(rentId, contract);
    }

    @Override
    public void deleteDocument(UUID documentId) {
        rentService.deleteDocument(documentId);
    }
}