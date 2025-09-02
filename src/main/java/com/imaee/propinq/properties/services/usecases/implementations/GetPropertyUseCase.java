package com.imaee.propinq.properties.services.usecases.implementations;

import com.imaee.propinq.properties.controllers.responses.PropertyDetailsResponse;
import com.imaee.propinq.properties.services.facades.interfaces.IPropertyFacade;
import com.imaee.propinq.properties.services.usecases.interfaces.IFindPropertyByIdUseCase;
import com.imaee.propinq.properties.services.usecases.interfaces.IGetPropertyUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.UUID;
import static com.imaee.propinq.properties.mappers.PropertyMapper.toPropertyDetailsResponse;

@Component
@AllArgsConstructor
public class GetPropertyUseCase implements IGetPropertyUseCase {
    private final IFindPropertyByIdUseCase findPropertyByIdUseCase;
    private final IPropertyFacade propertyFacade;

    @Override
    public PropertyDetailsResponse getPropertyDetails(UUID propertyId) {
        final var property = findPropertyByIdUseCase.findProperty(propertyId);
        final var imagesURLS = propertyFacade.getImagesURLs(property);
        return toPropertyDetailsResponse(property, imagesURLS);
    }
}
