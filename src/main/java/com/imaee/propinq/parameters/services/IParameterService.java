package com.imaee.propinq.parameters.services;

import java.util.List;

public interface IParameterService {
    Double maxPrice();
    Double minPrice();
    List<Integer> rooms();
    List<Integer> bathrooms();
}