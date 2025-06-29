package com.imaee.propinq.neighborhood.controllers.implementations;

import com.imaee.propinq.neighborhood.controllers.interfaces.IProvinceController;
import com.imaee.propinq.neighborhood.controllers.responses.ProvinceResponse;
import com.imaee.propinq.neighborhood.services.interfaces.IProvinceService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class ProvinceController implements IProvinceController {
    private IProvinceService provinceService;

    @Override
    public ResponseEntity<List<ProvinceResponse>> getProvinces() {
        return provinceService.getProvinces();
    }
}
