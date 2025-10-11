package com.imaee.propinq.provinces.controllers.implementations;

import com.imaee.propinq.provinces.controllers.interfaces.IProvinceController;
import com.imaee.propinq.provinces.controllers.responses.ProvinceResponse;
import com.imaee.propinq.provinces.services.interfaces.IProvinceService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
public class ProvinceController implements IProvinceController {

    private final IProvinceService provinceService;

    @Override
    public List<ProvinceResponse> getProvinces() {
        return provinceService.getProvinces();
    }

    @Override
    public ProvinceResponse getProvince( UUID provinceId) {
        return provinceService.getProvince(provinceId);
    }
}
