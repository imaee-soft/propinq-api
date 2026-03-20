#!/usr/bin/env bash
set -euo pipefail

# Normalizar fin de línea (CRLF->LF) si los scripts vinieran con CRLF (host Windows)
if command -v dos2unix >/dev/null 2>&1; then
  dos2unix /app/scripts/*.sh 2>/dev/null || true
fi
# Asegurar permisos de ejecución por si el bind mount viene sin +x
chmod +x /app/scripts/*.sh 2>/dev/null || true

# Defaults sensatos
export OSM_STATE_DIR="${OSM_STATE_DIR:-/app/state}"
export POI_SCHEDULER_REGION="${POI_SCHEDULER_REGION:-south-america/argentina-latest}"
export POI_SCHEDULER_RUN_DOWNLOAD="${POI_SCHEDULER_RUN_DOWNLOAD:-true}"
export POI_SCHEDULER_RUN_EXTRACTION="${POI_SCHEDULER_RUN_EXTRACTION:-true}"
export SPRING_PROFILES_ACTIVE="${SPRING_PROFILES_ACTIVE:-dev}"

# Throttle por defecto: 365 días (podés override con env)
export OSM_DOWNLOAD_THROTTLE_DAYS="${OSM_DOWNLOAD_THROTTLE_DAYS:-365}"

# Descarga/extracción previa (idempotente según MD5 + throttle)
if [[ "${POI_SCHEDULER_RUN_DOWNLOAD}" == "true" || "${POI_SCHEDULER_RUN_EXTRACTION}" == "true" ]]; then
  echo "[EntryPoint] Ejecutando update-argentina.sh (STATE_DIR=${OSM_STATE_DIR})"
  /app/scripts/update-argentina.sh /app/data /app/out "${POI_SCHEDULER_REGION}" || true
fi

# Levantar en dev con hot reload
exec ./mvnw -Dspring-boot.run.profiles="${SPRING_PROFILES_ACTIVE}" -DskipTests spring-boot:run