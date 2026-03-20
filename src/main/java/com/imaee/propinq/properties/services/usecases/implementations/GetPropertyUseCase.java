package com.imaee.propinq.properties.services.usecases.implementations;

import com.imaee.propinq.auth.services.interfaces.IAuthenticatedUserService;
import com.imaee.propinq.favorites.data.models.Favorite;
import com.imaee.propinq.favorites.data.repositories.IFavoriteRepository;
import com.imaee.propinq.properties.controllers.responses.PropertyDetailsResponse;
import com.imaee.propinq.properties.data.models.Property;
import com.imaee.propinq.properties.services.facades.interfaces.IPropertyFacade;
import com.imaee.propinq.properties.services.usecases.interfaces.IFindPropertyByIdUseCase;
import com.imaee.propinq.properties.services.usecases.interfaces.IGetPropertyUseCase;
import com.imaee.propinq.contacts.data.models.Contact;
import com.imaee.propinq.contacts.data.repositories.IContactRepository;
import com.imaee.propinq.users.data.models.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static com.imaee.propinq.properties.mappers.PropertyMapper.toPropertyDetailsResponse;
import static java.util.Optional.ofNullable;

@Component
@AllArgsConstructor
public class GetPropertyUseCase implements IGetPropertyUseCase {

    private final IAuthenticatedUserService authenticatedUserService;
    private final IFindPropertyByIdUseCase findPropertyByIdUseCase;
    private final IPropertyFacade propertyFacade;
    private final IFavoriteRepository favoriteRepository;
    private final IContactRepository contactRepository;

    @Override
    public PropertyDetailsResponse getPropertyDetails(UUID propertyId) {
        final var user = authenticatedUserService.safelyGetLoggedUser();
        final var property = findPropertyByIdUseCase.findProperty(propertyId);
        final var imagesURLS = propertyFacade.getImagesURLs(property);
        return toPropertyDetailsResponse(
                property,
                imagesURLS,
                getFavoriteId(user, property),
                getContactId(user, property)
        );
    }

    private UUID getFavoriteId(User user, Property property) {
        return ofNullable(user)
                .map(loggedUser -> favoriteRepository.findByUserIDAndPropertyID(loggedUser, property))
                .map(Favorite::getFavoriteId)
                .orElse(null);
    }

    private UUID getContactId(User user, Property property) {
        return ofNullable(user)
                .flatMap(loggedUser -> contactRepository.findByPropertyAndIssuer(property, loggedUser))
                .map(Contact::getContactId)
                .orElse(null);
    }
}
