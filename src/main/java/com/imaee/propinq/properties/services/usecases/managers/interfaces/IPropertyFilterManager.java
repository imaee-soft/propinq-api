package com.imaee.propinq.properties.services.usecases.managers.interfaces;

import com.imaee.propinq.properties.controllers.requests.PropertyFilterRequest;
import com.imaee.propinq.properties.controllers.responses.PropertyResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

/**
 * Interfaz para el manejo inteligente de filtros de propiedades.
 * Utiliza el patrón Strategy para seleccionar automáticamente la estrategia de filtrado más eficiente.
 */
@Tag(name = "Property Filter Manager", description = "Gestión inteligente de filtros de propiedades")
public interface IPropertyFilterManager {
    
    /**
     * Aplica filtros de forma inteligente, seleccionando la estrategia más eficiente
     * basada en los campos activos en el filtro.
     * 
     * @param filter Objeto de filtro que puede contener múltiples tipos de filtros:
     *               - Filtros de atributos (tipo de edificio, precio, habitaciones, etc.)
     *               - Filtros de ubicación (coordenadas y radio)
     *               - Filtros de POI (puntos de interés cercanos)
     * @return Lista de propiedades que cumplen con los criterios especificados
     * @throws IllegalArgumentException si no hay estrategia disponible para el filtro
     */
    @Operation(
        summary = "Aplicar filtros inteligentes a propiedades",
        description = """
            Aplica filtros de manera inteligente utilizando el patrón Strategy. 
            El sistema analiza automáticamente qué campos del filtro están activos y selecciona 
            la estrategia de filtrado más eficiente, o combina múltiples estrategias si es necesario.
            
            **Tipos de filtros soportados:**
            - **Atributos**: Tipo de edificio, rango de precios, habitaciones, baños, mascotas, área
            - **Ubicación**: Coordenadas geográficas con radio de búsqueda
            - **POI**: Búsqueda por proximidad a puntos de interés específicos
            
            **Estrategias automáticas:**
            - Si solo hay filtros de atributos → Usa AttributeFilterStrategy
            - Si solo hay filtros de ubicación → Usa LocationFilterStrategy  
            - Si solo hay filtros de POI → Usa PoiFilterStrategy
            - Si hay múltiples tipos → Combina estrategias de forma optimizada
            """
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Propiedades filtradas exitosamente"),
        @ApiResponse(responseCode = "400", description = "Filtro inválido o no hay estrategia disponible"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    List<PropertyResponse> applyFilters(
        @Parameter(
            description = """
                Objeto de filtro compuesto que puede contener:
                
                **attributes** (AttributeFilterRequest):
                - buildingType: Tipo de edificio (HOUSE, APARTMENT, etc.)
                - priceMin/priceMax: Rango de precios en la moneda base
                - bedrooms: Número mínimo de habitaciones
                - bathrooms: Número mínimo de baños
                - petsAllowed: Si se permiten mascotas (true/false)
                - areaMin/areaMax: Rango de área en metros cuadrados
                
                **location** (LocationFilterRequest):
                - latitude: Latitud del punto central
                - longitude: Longitud del punto central  
                - radiusKm: Radio de búsqueda en kilómetros
                
                **poi** (PoiFilterRequest):
                - poiType: Tipo de POI a buscar
                - radiusKm: Radio de búsqueda desde el POI
                - north/south/east/west: Coordenadas del viewport (alternativo)
                - limit: Número máximo de POIs a considerar
                
                **Ejemplos de uso:**
                - Solo atributos: `{"attributes": {"buildingType": "HOUSE", "priceMax": 500000}}`
                - Solo ubicación: `{"location": {"latitude": -34.603, "longitude": -58.382, "radiusKm": 5}}`
                - Combinado: `{"attributes": {"bedrooms": 2}, "location": {"latitude": -34.603, "longitude": -58.382, "radiusKm": 3}}`
                """,
            required = true
        )
        PropertyFilterRequest filter
    );
}