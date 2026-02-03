package com.imaee.propinq.buildings.services.usecases.implementations;

import com.imaee.propinq.auth.services.interfaces.IAuthenticatedUserService;
import com.imaee.propinq.buildings.controllers.responses.BuildingDetailsResponse;
import com.imaee.propinq.buildings.data.models.Building;
import com.imaee.propinq.buildings.services.facades.interfaces.IBuildingFacade;
import com.imaee.propinq.buildings.services.usecases.interfaces.IFindBuildingByIdUseCase;
import com.imaee.propinq.buildings.services.usecases.interfaces.IGetBuildingUseCase;
import com.imaee.propinq.favorites.data.models.Favorite;
import com.imaee.propinq.favorites.data.repositories.IFavoriteRepository;
import com.imaee.propinq.users.data.models.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;
import static com.imaee.propinq.buildings.mappers.BuildingMapper.toBuildingDetailsResponse;
import static java.util.Optional.ofNullable;

@Component
@AllArgsConstructor
public class GetBuildingUseCase implements IGetBuildingUseCase {

    private final IAuthenticatedUserService authenticatedUserService;
    private final IFindBuildingByIdUseCase findBuildingByIdUseCase;
    private final IBuildingFacade buildingFacade;
    private final IFavoriteRepository favoriteRepository;

    @Override
    public BuildingDetailsResponse getBuilding(UUID buildingId) {
        final var user = authenticatedUserService.safelyGetLoggedUser();
        final var building = findBuildingByIdUseCase.findBuilding(buildingId);
        return toBuildingDetailsResponse(
                building,
                buildingFacade.getImagesURLs(building),
                getFavoriteId(user, building)
        );
    }

    private UUID getFavoriteId(User user, Building building) {
        return ofNullable(user)
                .map(loggedUser -> favoriteRepository.findByUserIDAndBuildingID(user, building))
                .map(Favorite::getFavoriteId)
                .orElse(null);
    }
}