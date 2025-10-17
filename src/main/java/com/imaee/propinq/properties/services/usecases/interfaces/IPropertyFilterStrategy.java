package com.imaee.propinq.properties.services.usecases.interfaces;

import com.imaee.propinq.properties.controllers.requests.PropertyFilterRequest;
import com.imaee.propinq.properties.controllers.responses.PropertyResponse;

import java.util.List;
import java.util.Set;

public interface IPropertyFilterStrategy {
    
    /**
     * Verifica si esta estrategia puede manejar el filtro dado
     */
    boolean canHandle(PropertyFilterRequest filter);
    
    /**
     * Obtiene la prioridad de la estrategia (mayor número = mayor prioridad)
     */
    int getPriority();
    
    /**
     * Aplica el filtro y devuelve los resultados
     */
    List<PropertyResponse> applyFilter(PropertyFilterRequest filter);
    
    /**
     * Devuelve los nombres de los campos que esta estrategia puede manejar
     * Solo necesita conocer SUS propios campos
     */
    Set<String> getHandledFields();
}