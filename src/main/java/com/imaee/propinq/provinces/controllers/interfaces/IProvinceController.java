package com.imaee.propinq.provinces.controllers.interfaces;

import com.imaee.propinq.config.utils.Endpoints;

import com.imaee.propinq.provinces.controllers.responses.ProvinceResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.UUID;

@RequestMapping(Endpoints.API + "/provinces")
@Tag(
        name = "Provinces",
        description = "Operations for managing and querying provinces."
)
public interface IProvinceController {
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Retrieves a list of all provinces with basic information.")
    List<ProvinceResponse> getProvinces();

    @GetMapping("/{provinceId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Retrieves detailed information about a specific province by its ID.")
    ProvinceResponse getProvince(@PathVariable UUID provinceId);
}
