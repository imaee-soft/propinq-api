package com.imaee.propinq.poi.data.repositories;

import com.imaee.propinq.poi.data.enums.PoiType;
import com.imaee.propinq.poi.data.models.Poi;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
public interface IPoiRepository extends JpaRepository<Poi, UUID> {
    @Query("""
           SELECT p FROM Poi p
           WHERE p.latitude BETWEEN :south AND :north
             AND (
                   (:crossesDateline = FALSE AND p.longitude BETWEEN :west AND :east)
                   OR
                   (:crossesDateline = TRUE AND (p.longitude >= :west OR p.longitude <= :east))
                 )
             AND ( :#{#types == null || #types.isEmpty()} = TRUE OR p.type IN :types )
           """)
    List<Poi> findWithinViewport(
            @Param("south") double south,
            @Param("north") double north,
            @Param("west") double west,
            @Param("east") double east,
            @Param("crossesDateline") boolean crossesDateline,
            @Param("types") Set<PoiType> types,
            Pageable pageable
    );
}
