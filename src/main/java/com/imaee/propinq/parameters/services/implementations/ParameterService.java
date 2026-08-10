package com.imaee.propinq.parameters.services.implementations;

import com.imaee.propinq.parameters.services.IParameterService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParameterService implements IParameterService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Double maxPrice() {
        Double result = entityManager
                .createQuery("SELECT MAX(p.price) FROM properties p", Double.class)
                .getSingleResult();
        return result != null ? result : 0.0;
    }

    @Override
    public Double minPrice() {
        Double result = entityManager
                .createQuery("SELECT MIN(p.price) FROM properties p", Double.class)
                .getSingleResult();
        return result != null ? result : 0.0;
    }

    @Override
    public List<Integer> rooms() {
        return entityManager
                .createQuery("SELECT DISTINCT p.bedrooms FROM properties p", Integer.class)
                .getResultList();
    }

    @Override
    public List<Integer> bathrooms() {
        return entityManager
                .createQuery("SELECT DISTINCT p.bathrooms FROM properties p", Integer.class)
                .getResultList();
    }
}
