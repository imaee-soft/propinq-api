package com.imaee.propinq.properties.services.implementations;

import com.imaee.propinq.properties.controllers.requests.PropertyTypeRequest;
import com.imaee.propinq.properties.controllers.responses.PropertyTypeResponse;
import com.imaee.propinq.properties.data.models.PropertyType;
import com.imaee.propinq.properties.data.repositories.IPropertyTypeRepository;
import com.imaee.propinq.properties.mappers.PropertyTypeMapper;
import com.imaee.propinq.properties.services.interfaces.IPropertyTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PropertyTypeService implements IPropertyTypeService {

    @Autowired
    private IPropertyTypeRepository propertyTypeRepository;

    private final String MSG_ALREADY_EXISTS = "PROPERTYTYPE WITH THIS NAME ALREADY EXISTS";
    private final String MSG_NOT_EXISTS = "PROPERTYTYPE WITH THIS ID NOT EXISTS";

    @Override
    public List<PropertyTypeResponse> getPropertyType() {
        List<PropertyType> propertyTypes = propertyTypeRepository.findByState(PropertyType.ACTIVE);
        return propertyTypes.stream().map(PropertyTypeMapper::toPropertyTypeResponse).toList();
    }

    @Override
    public PropertyTypeResponse createPropertyType(PropertyTypeRequest propertyTypeRequest) {
        PropertyType model = PropertyTypeMapper.toPropertyType(propertyTypeRequest);
        Optional<PropertyType> propertyTypeOptional = propertyTypeRepository.findByName(model.getName());

        if(propertyTypeOptional.isPresent()){
            PropertyType existingPropertyType = propertyTypeOptional.get();
            if(existingPropertyType.isState() == PropertyType.REMOVED){
                existingPropertyType.setState(PropertyType.ACTIVE);
                existingPropertyType.setName(model.getName());
                existingPropertyType.setDescription(model.getDescription());
                return PropertyTypeMapper.toPropertyTypeResponse(propertyTypeRepository.save(existingPropertyType));
            }else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MSG_ALREADY_EXISTS);
            }
        }
        return PropertyTypeMapper.toPropertyTypeResponse(propertyTypeRepository.save(model));
    }

    @Override
    public PropertyTypeResponse updatePropertyType(PropertyTypeRequest propertyTypeRequest, UUID id) {
        PropertyType model = PropertyTypeMapper.toPropertyType(propertyTypeRequest);
        Optional<PropertyType> propertyTypeOptional = propertyTypeRepository.findById(id);

        if (propertyTypeOptional.isPresent()) {
            if (propertyTypeOptional.get().isState() == PropertyType.REMOVED) {
                {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "NO SE PUDO ACTUALIZAR, EL TIPO DE VIVIENDA ESTÁ ELIMINADO");
                }
            }
            PropertyType propertyType = propertyTypeOptional.get();
            propertyType.setName(model.getName());
            propertyType.setDescription(model.getDescription());
            return PropertyTypeMapper.toPropertyTypeResponse(propertyTypeRepository.save(propertyType));
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MSG_NOT_EXISTS);
    }

    @Override
    public ResponseEntity<Void> deletePropertyType(UUID id) {
        PropertyType model = propertyTypeRepository.findById(id).orElse(null);
        if (model == null || model.isState() == PropertyType.REMOVED) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "NO SE PUDO ELIMINAR, EL TIPO DE VIVIENDA YA ESTÁ ELIMINADO");
        }

        model.setState(PropertyType.REMOVED);
        propertyTypeRepository.save(model);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> recoverPropertyType(UUID id) {
        PropertyType model = propertyTypeRepository.findById(id).orElse(null);
        if (model == null || model.isState() == PropertyType.ACTIVE) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "NO SE PUDO RECUPERAR, EL TIPO DE VIVIENDA YA ESTÁ ACTIVO");
        }
        model.setState(PropertyType.ACTIVE);
        propertyTypeRepository.save(model);
        return ResponseEntity.ok().build(); //respuesta con 200
    }



}



