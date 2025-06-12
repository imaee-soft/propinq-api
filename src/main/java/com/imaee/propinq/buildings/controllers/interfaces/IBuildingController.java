package com.imaee.propinq.buildings.controllers.interfaces;

import com.imaee.propinq.buildings.controllers.responses.BuildingResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@RequestMapping("/api/v1/buildings")
public interface IBuildingController {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<BuildingResponse> getBuildings();
}
