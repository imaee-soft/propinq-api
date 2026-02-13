package com.imaee.propinq.parameters.controllers.implementations;

import com.imaee.propinq.parameters.controllers.IParameterController;
import com.imaee.propinq.parameters.services.IParameterService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class ParameterController implements IParameterController {

    private final IParameterService parameterService;

    @Override
    public Double maxPrice() {
        return parameterService.maxPrice();
    }

    @Override
    public Double minPrice() {
        return parameterService.minPrice();
    }
}