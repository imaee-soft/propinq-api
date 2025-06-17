package com.imaee.propinq.neighborhood.services.implementations;

import com.imaee.propinq.neighborhood.controllers.requests.LocalityRequest;
import com.imaee.propinq.neighborhood.controllers.responses.LocalityResponse;
import com.imaee.propinq.neighborhood.data.models.Locality;
import com.imaee.propinq.neighborhood.data.repositories.ILocalityRepository;
import com.imaee.propinq.neighborhood.mappers.LocalityMapper;
import com.imaee.propinq.neighborhood.services.interfaces.ILocalityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LocalityService implements ILocalityService {

    @Autowired
    private ILocalityRepository repository;

    private final String MSG_ALREADY_EXITS = "LOCALITY WHIT THIS NAME ALREADY EXITS";

    @Override
    public LocalityResponse create(LocalityRequest localityRequest) {

        Locality model = LocalityMapper.toEntity(
                localityRequest.name()
        );

        Optional<Locality> created = repository.findByName(model.getName());

        if(created.isPresent()) {
            throw new IllegalArgumentException(MSG_ALREADY_EXITS);
        }

        return LocalityMapper.toResponse(repository.save(model));
    }

}
