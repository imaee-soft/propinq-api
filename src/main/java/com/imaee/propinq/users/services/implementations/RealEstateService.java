package com.imaee.propinq.users.services.implementations;

import com.imaee.propinq.users.controllers.requests.RealEstateRequest;
import com.imaee.propinq.users.controllers.responses.RealEstateResponse;
import com.imaee.propinq.users.data.models.RealEstate;
import com.imaee.propinq.users.data.repositories.IRealEstateRepository;
import com.imaee.propinq.users.mappers.RealEstateMapper;
import com.imaee.propinq.users.services.interfaces.IRealEstateService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
public class RealEstateService implements IRealEstateService {
    private final static String COMPANY_NAME_EXISTS = "A REALESTATE with this company name already exists or with this CUIT the number";
    private final IRealEstateRepository realEstateRepository;

    @Override
    public RealEstateResponse registerRealEstate(RealEstateRequest realEstateRequest) {

        //Valido si existe alguna REALESTATE con el mismo nombre de compañia o con el mismo CUIT
        if(realEstateRepository.findByCompanyName(realEstateRequest.companyName()).isPresent()
            && realEstateRepository.findByCuit(realEstateRequest.cuit()).isPresent()) {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, COMPANY_NAME_EXISTS);
        }
        //Creo la REALESTATE
        RealEstate realEstate = RealEstateMapper.toRealEstateEntity(realEstateRequest);

        realEstateRepository.save(realEstate);
        return RealEstateMapper.toRealEstateResponse(realEstate);
    }
}
