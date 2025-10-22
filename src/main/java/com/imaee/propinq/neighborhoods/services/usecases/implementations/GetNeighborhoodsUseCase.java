package com.imaee.propinq.neighborhoods.services.usecases.implementations;

import com.imaee.propinq.neighborhoods.controllers.responses.NeighborhoodResponse;
import com.imaee.propinq.neighborhoods.data.repositories.INeighborhoodRepository;
import com.imaee.propinq.neighborhoods.mappers.NeighborhoodMapper;
import com.imaee.propinq.neighborhoods.services.usecases.interfaces.IGetNeighborhoodsUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class GetNeighborhoodsUseCase implements IGetNeighborhoodsUseCase {

    private final INeighborhoodRepository neighborhoodRepository;

    @Override
    public List<NeighborhoodResponse> getNeighborhoods() {
        return neighborhoodRepository
                .findAll()
                .stream()
                .map(NeighborhoodMapper::toNeighborhoodResponse)
                .collect(Collectors.toList());
    }

}
