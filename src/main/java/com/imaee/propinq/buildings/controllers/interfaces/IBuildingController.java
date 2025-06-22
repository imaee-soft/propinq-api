package com.imaee.propinq.buildings.controllers.interfaces;

import com.imaee.propinq.buildings.controllers.responses.BuildingDetailsResponse;
import com.imaee.propinq.buildings.controllers.responses.BuildingResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/v1/buildings")
public interface IBuildingController {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<BuildingResponse> getBuildings();

    @GetMapping("/{buildingId}")
    @ResponseStatus(HttpStatus.OK)
    BuildingDetailsResponse getBuilding(@PathVariable UUID buildingId);
}
