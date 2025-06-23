package com.imaee.propinq.buildings.controllers.interfaces;

import com.imaee.propinq.buildings.controllers.responses.BuildingResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/v1/buildings")
public interface IBuildingController {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<BuildingResponse> getBuildings();

    @GetMapping("/{buildingId}")
    @ResponseStatus(HttpStatus.OK)
    BuildingResponse getBuilding(@PathVariable UUID buildingId);
}
