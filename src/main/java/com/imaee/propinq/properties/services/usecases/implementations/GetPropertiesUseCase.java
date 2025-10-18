package com.imaee.propinq.properties.services.usecases.implementations;

import com.imaee.propinq.properties.controllers.responses.PropertyDetailsResponse;
import com.imaee.propinq.properties.data.repositories.IPropertyRepository;
import com.imaee.propinq.properties.mappers.PropertyMapper;
import com.imaee.propinq.properties.controllers.responses.PropertyResponse;
import com.imaee.propinq.properties.services.facades.interfaces.IPropertyFacade;
import com.imaee.propinq.properties.services.usecases.interfaces.IGetPropertiesUseCase;
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
import java.util.UUID;


@Component
@AllArgsConstructor
public class GetPropertiesUseCase implements IGetPropertiesUseCase {
    private final IPropertyRepository propertyRepository;
    private final IPropertyFacade propertyFacade;
    private final IUserService userService;

    @Override
    public List<PropertyResponse> getProperties() {
        return propertyRepository.findAllByDeletedFalseAndBuildingIsNull()
                .stream()
                .map(PropertyMapper::toPropertyResponse)
                .toList();
    }

    @Override
    public List<PropertyDetailsResponse> getBuildingProperties(UUID buildingId) {
        return propertyRepository.findAllByDeletedFalseAndBuilding_BuildingId(buildingId)
                .stream()
                .map(p -> PropertyMapper.toPropertyDetailsResponse(p, propertyFacade.getImagesURLs(p)))
                .toList();

    }

    @Override
    public Page<PropertyDetailsResponse> getPropertiesDetails(int page, int size) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userService.findUserByEmail(email);
        Pageable pageable = PageRequest.of(page, size);
        return propertyRepository.findAllByUser_UserId(user.getUserId(), pageable)
                .map(p -> PropertyMapper.toPropertyDetailsResponse(p, propertyFacade.getImagesURLs(p)));

    }
}
