package com.imaee.propinq.neighborhood.services.interfaces;

import com.imaee.propinq.neighborhood.controllers.responses.ProvinceResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IProvinceService {
    ResponseEntity<List<ProvinceResponse>> getProvinces();
}
