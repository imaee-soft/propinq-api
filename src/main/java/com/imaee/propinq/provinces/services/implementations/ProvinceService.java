package com.imaee.propinq.provinces.services.implementations;

import com.imaee.propinq.provinces.controllers.responses.ProvinceResponse;
import com.imaee.propinq.provinces.services.interfaces.IProvinceService;
import com.imaee.propinq.provinces.services.usecases.interfaces.IGetProvinceUseCase;
import com.imaee.propinq.provinces.services.usecases.interfaces.IGetProvincesUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ProvinceService implements IProvinceService {

    private final IGetProvincesUseCase getProvincesUseCase;
    private final IGetProvinceUseCase getProvinceUseCase;

    @Override
    public List<ProvinceResponse> getProvinces() {
        return getProvincesUseCase.getProvinces();
    }

    @Override
    public ProvinceResponse getProvince( UUID provinceId) {
        return getProvinceUseCase.getProvince(provinceId);
    }

}
