package com.imaee.propinq.reports.services.interfaces;

import java.util.Map;

public interface IMetabaseEmbedService {
    /**
     * Genera una URL segura de embedding Metabase para preguntas o dashboards,
     * con parámetros de filtro personalizados.
     *
     * @param type         "question" o "dashboard"
     * @param resourceId   ID de la pregunta o dashboard en Metabase
     * @param params       mapa de parámetros para filtros (ej: owner_id, date, etc)
     * @return             URL lista para incrustar en un iframe seguro
     */
    String getEmbedUrl(String type, Long resourceId, Map<String, Object> params);
}