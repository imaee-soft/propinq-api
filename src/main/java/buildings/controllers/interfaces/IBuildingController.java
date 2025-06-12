package buildings.controllers.interfaces;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequestMapping("/api/v1/buildings")
public interface IBuildingController {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<BuildingResponse> getBuildings();
}
