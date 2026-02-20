package com.imaee.propinq.rents.services.interfaces;

import com.imaee.propinq.projections.responses.Projection;
import com.imaee.propinq.rents.controllers.requests.CancelRentRequest;
import com.imaee.propinq.rents.controllers.requests.RentDocumentRequest;
import com.imaee.propinq.rents.controllers.requests.RentRequest;
import com.imaee.propinq.rents.controllers.responses.RentDetail;
import com.imaee.propinq.rents.controllers.responses.SaveRentResponse;
import com.imaee.propinq.rents.controllers.responses.SimpleRent;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface IRentService {
    SaveRentResponse saveRent(RentRequest rentRequest, MultipartFile contract);
    Page<SimpleRent> getOwnerRents(Integer pageNumber, Integer pageSize, String surname);
    Page<SimpleRent> getTenantRents(Integer pageNumber, Integer pageSize);
    RentDetail getRent(UUID rentId);
    List<Projection> getRentProjection(UUID rentId);
    void saveDocument(RentDocumentRequest rentDocumentRequest, MultipartFile document);
    void cancelRent(UUID rentId, CancelRentRequest cancelRentRequest);
}