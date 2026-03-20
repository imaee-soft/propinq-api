package com.imaee.propinq.buildings.services.usecases.implementations;

import com.imaee.propinq.buildings.controllers.responses.BuildingDetailsResponse;
import com.imaee.propinq.buildings.controllers.responses.BuildingResponse;
import com.imaee.propinq.buildings.data.models.Building;
import com.imaee.propinq.buildings.data.repositories.IBuildingRepository;
import com.imaee.propinq.buildings.mappers.BuildingMapper;
import com.imaee.propinq.buildings.services.facades.interfaces.IBuildingFacade;
import com.imaee.propinq.buildings.services.usecases.interfaces.IGetBuildingsUseCase;
import com.imaee.propinq.users.data.models.User;
import com.imaee.propinq.users.services.interfaces.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import java.util.List;

@AllArgsConstructor
@Component
public class GetBuildingsUseCase implements IGetBuildingsUseCase {

    private final IBuildingRepository buildingRepository;
    private final IBuildingFacade buildingFacade;
    private final IUserService userService;

    @Override
    public List<BuildingResponse> getBuildings() {
        return buildingRepository.findAllByDeletedFalse()
                .stream()
                .map(BuildingMapper::toBuildingResponse)
                .toList();
    }

    @Override
    public Page<BuildingDetailsResponse> getBuildingsDetails(int page, int size) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userService.findUserByEmail(email);

        Pageable pageable = PageRequest.of(page, size);
        Page<Building> buildingsPage = buildingRepository.findAllByUser(user, pageable);
        return buildingsPage.map(building -> BuildingMapper.toBuildingDetailsResponse(building, buildingFacade.getImagesURLs(building)));
    }
}