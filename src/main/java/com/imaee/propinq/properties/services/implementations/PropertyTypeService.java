package com.imaee.propinq.properties.services.implementations;

import com.imaee.propinq.properties.controllers.requests.PropertyTypeRequest;
import com.imaee.propinq.properties.controllers.responses.PropertyTypeResponse;
import com.imaee.propinq.properties.data.models.PropertyType;
import com.imaee.propinq.properties.data.repositories.IPropertyTypeRepository;
import com.imaee.propinq.properties.mappers.PropertyTypeMapper;
import com.imaee.propinq.properties.services.interfaces.IPropertyTypeService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PropertyTypeService implements IPropertyTypeService {

    private IPropertyTypeRepository propertyTypeRepository;

    private final String MSG_ALREADY_EXISTS = "propertytype with this name already exists";
    private final String MSG_NOT_EXISTS = "PropertyType does not exist";
    private final String MSG_IS_DELETED = "NO SE PUDO ACTUALIZAR, EL TIPO DE VIVIENDA ESTÁ ELIMINADO";
    private final String MSG_IS_NOT_DELETED = "EL TIPO DE VIVIENDA NO ESTÁ ELIMINADO";
    @Override
    public List<PropertyTypeResponse> getPropertyType() {
        return propertyTypeRepository.findByDeletedFalse().stream()
                .map(PropertyTypeMapper::toPropertyTypeResponse)
                .toList();
    }

    @Override
    public List<PropertyTypeResponse> getPropertyTypeAll() {
        return propertyTypeRepository.findAll().stream()
                .map(PropertyTypeMapper::toPropertyTypeResponse)
                .toList();
    }

    @Override
    public PropertyTypeResponse createPropertyType(PropertyTypeRequest propertyTypeRequest) {
        throwExceptionIfPropertyTypeAlreadyExists(propertyTypeRequest.name());
        final var propertyType = PropertyTypeMapper.toPropertyType(propertyTypeRequest);
        return PropertyTypeMapper.toPropertyTypeResponse(
                propertyTypeRepository.save(propertyType)
        );
    }
    private void throwExceptionIfPropertyTypeAlreadyExists(String propertyTypeName) {
        if (propertyTypeRepository.existsByNameAndDeletedFalse(propertyTypeName)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MSG_ALREADY_EXISTS);
        }
    }

    @Override
    public PropertyTypeResponse updatePropertyType(PropertyTypeRequest propertyTypeRequest, UUID id) {
        final var propertyType = findByIdOrThrowException(id);
        throwExceptionIfPropertyTypeIsDeleted(propertyType);
        propertyType.setName(propertyTypeRequest.name());
        propertyType.setDescription(propertyTypeRequest.description());
        return PropertyTypeMapper.toPropertyTypeResponse(propertyTypeRepository.save(propertyType));
    }

    private PropertyType findByIdOrThrowException(UUID propertyTypeId) {
        return propertyTypeRepository.findById(propertyTypeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, MSG_NOT_EXISTS));
    }

    private void throwExceptionIfPropertyTypeIsDeleted(PropertyType propertyType) {
        if (propertyType.isDeleted())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MSG_IS_DELETED);
    }

    @Override
    public void deletePropertyType(UUID id) {
        PropertyType model = propertyTypeRepository.findById(id).orElse(null);
        final var propertyType = findByIdOrThrowException(id);
        throwExceptionIfPropertyTypeIsDeleted(propertyType);
        model.setDeleted(true);
        propertyTypeRepository.save(propertyType);
    }

    @Override
    public void recoverPropertyType(UUID id) {
        final var propertyType = findByIdOrThrowException(id);
        throwExceptionIfPropertyTypeIsNotDeleted(propertyType);
        // Agreguemos una validación para ver si existe el mismo PropertyType pero activo
        propertyType.setDeleted(false);
        propertyTypeRepository.save(propertyType);
    }

    private void throwExceptionIfPropertyTypeIsNotDeleted(PropertyType propertyType) {
        if (!propertyType.isDeleted())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MSG_IS_NOT_DELETED);
    }
}