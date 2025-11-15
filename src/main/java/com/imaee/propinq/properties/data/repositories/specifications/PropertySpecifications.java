package com.imaee.propinq.properties.data.repositories.specifications;

import org.springframework.data.jpa.domain.Specification;

import com.imaee.propinq.properties.controllers.requests.AttributeFilterRequest;
import com.imaee.propinq.properties.data.models.Property;
import jakarta.persistence.criteria.Predicate;
import java.util.Collection;
import java.util.UUID;

public class PropertySpecifications {
    private PropertySpecifications(){
    }


    public static Specification<Property> attributeFilter(AttributeFilterRequest filter) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            if (filter.getBuildingType() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("building").get("buildingType"), filter.getBuildingType()));
            }
            if (filter.getPriceMin() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(root.get("price"), filter.getPriceMin()));
            }
            if (filter.getPriceMax() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThanOrEqualTo(root.get("price"), filter.getPriceMax()));
            }
            if (filter.getBedrooms() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("bedrooms"), filter.getBedrooms()));
            }
            if (filter.getBathrooms() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("bathrooms"), filter.getBathrooms()));
            }
            if (filter.getPetsAllowed() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("petsAllowed"), filter.getPetsAllowed()));
            }
            if (filter.getAreaMin() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(root.get("area"), filter.getAreaMin()));
            }
            if (filter.getAreaMax() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThanOrEqualTo(root.get("area"), filter.getAreaMax()));
            }
            return predicate;
        };
    }

    public static Specification<Property> notDeleted() {
        return (root, query, cb) -> cb.isFalse(root.get("deleted"));
    }

    public static Specification<Property> inBuilding() {
        return (root, query, cb) -> cb.isNotNull(root.get("building"));
    }

    public static Specification<Property> inBuildings(Collection<UUID> buildingIds) {
        return (root, query, cb) -> root.get("building").get("buildingId").in(buildingIds);
    }

    public static Specification<Property> standalone() {
        return (root, query, cb) -> cb.isNull(root.get("building"));
    }
}
