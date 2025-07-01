package com.imaee.propinq.neighborhood.controllers.interfaces;

import com.imaee.propinq.neighborhood.controllers.responses.ProvinceResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@RequestMapping("/provinces")
public interface IProvinceController {
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<ProvinceResponse> getProvinces();
}
