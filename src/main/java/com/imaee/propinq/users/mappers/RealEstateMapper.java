package com.imaee.propinq.users.mappers;

import com.imaee.propinq.users.controllers.requests.RealEstateRequest;
import com.imaee.propinq.users.controllers.responses.RealEstateResponse;
import com.imaee.propinq.users.data.enums.Role;
import com.imaee.propinq.users.data.models.RealEstate;

public class RealEstateMapper {
    //mapper de entidad --> record
    public static RealEstateResponse toRealEstateResponse(RealEstate realEstate) {
        return RealEstateResponse.builder()
                .userId(realEstate.getUserId())
                .username(realEstate.getUsername())
                .firstName(realEstate.getFirstName())
                .lastName(realEstate.getLastName())
                .email(realEstate.getEmail())
                .phoneNumber(realEstate.getPhoneNumber())
                .address(realEstate.getAddress())
                .companyName(realEstate.getCompanyName())
                .cuit(realEstate.getCuit())
                .legalName(realEstate.getLegalName())
                .role(Role.REAL_ESTATE.name())
                .build();
    }
    //record --> entidad
    public static RealEstate toRealEstateEntity(RealEstateRequest realEstateRequest) {
        return RealEstate.builder()
                .username(realEstateRequest.username())
                .password(realEstateRequest.password())
                .firstName(realEstateRequest.firstName())
                .lastName(realEstateRequest.lastName())
                .email(realEstateRequest.email())
                .phoneNumber(realEstateRequest.phoneNumber())
                .address(realEstateRequest.address())
                .companyName(realEstateRequest.companyName())
                .cuit(realEstateRequest.cuit())
                .legalName(realEstateRequest.legalName())
                .build();
    }
}