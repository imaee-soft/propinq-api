package com.imaee.propinq.parameters.services.implementations;

import com.imaee.propinq.parameters.services.IParameterService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;

@Service
public class ParameterService implements IParameterService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Double maxPrice() {
        return entityManager
                .createQuery("SELECT MAX(p.price) FROM properties p", Double.class)
                .getSingleResult();
    }

    @Override
    public Double minPrice() {
        return entityManager
                .createQuery("SELECT MIN(p.price) FROM properties p", Double.class)
                .getSingleResult();
    }
}