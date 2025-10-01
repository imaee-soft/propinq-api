package com.imaee.propinq.provinces.services.usecases.implementations;

import com.imaee.propinq.provinces.controllers.responses.ProvinceResponse;
import com.imaee.propinq.provinces.data.models.Province;
import com.imaee.propinq.provinces.data.repositories.IProvinceRepository;
import com.imaee.propinq.provinces.mappers.ProvinceMapper;
import com.imaee.propinq.provinces.services.usecases.interfaces.IGetProvinceUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

import static com.imaee.propinq.provinces.utils.Constants.PROVINCE_NOT_FOUND;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Component
@AllArgsConstructor
public class GetProvinceUseCase implements IGetProvinceUseCase {

    private final IProvinceRepository provinceRepository;

    @Override
    public ProvinceResponse getProvince(UUID provinceId) {
        return ProvinceMapper.toProvinceResponse(findProvinceByIdOrThrowException(provinceId));
    }

    @Override
    public Province findProvinceByIdOrThrowException(UUID provinceId){
        return provinceRepository.findById(provinceId)
                .orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, PROVINCE_NOT_FOUND));
    }
}
