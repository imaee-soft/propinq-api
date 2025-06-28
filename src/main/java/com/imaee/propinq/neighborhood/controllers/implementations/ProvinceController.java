package com.imaee.propinq.neighborhood.controllers.implementations;

import com.imaee.propinq.neighborhood.controllers.interfaces.IProvinceController;
import com.imaee.propinq.neighborhood.controllers.responses.ProvinceResponse;
import com.imaee.propinq.neighborhood.services.interfaces.IProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProvinceController implements IProvinceController {
    @Autowired
    private IProvinceService provinceService;

    @Override
    public ResponseEntity<List<ProvinceResponse>> getProvinces() {
        return provinceService.getProvinces();
    }
}
