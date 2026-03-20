package com.imaee.propinq.parameters.controllers.implementations;

import com.imaee.propinq.parameters.controllers.IParameterController;
import com.imaee.propinq.parameters.services.IParameterService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @Override
    public List<Integer> rooms() {
        return parameterService.rooms();
    }

    @Override
    public List<Integer> bathrooms() {
        return parameterService.bathrooms();
    }
}