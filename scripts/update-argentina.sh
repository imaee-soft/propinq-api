#!/usr/bin/env bash
set -euo pipefail

# Orquestador: consulta MD5 remoto y ejecuta descarga+extracción si corresponde.
# Ahora incorpora throttle para evitar descargar más de 1 vez cada N días.
#
# Uso:
#   update-argentina.sh [DATA_DIR] [OUT_DIR] [REGION]
# Ejemplo:
#   update-argentina.sh data out south-america/argentina-latest

DATA_DIR="${1:-data}"
OUT_DIR="${2:-out}"
REGION="${3:-south-america/argentina-latest}"

BASE="https://download.geofabrik.de"
PBF_URL="${BASE}/${REGION}.osm.pbf"
MD5_URL="${PBF_URL}.md5"

# Construimos el nombre esperado del PBF desde REGION
PBF_FILE="${DATA_DIR}/$(basename "${REGION}.osm.pbf")"

STATE_DIR="${OSM_STATE_DIR:-${XDG_STATE_HOME:-$HOME/.local/state}/propinq}"
mkdir -p "$STATE_DIR"
BASENAME="$(basename "$REGION")"                            # argentina-latest
STAMP_IMPORTED="${STATE_DIR}/${BASENAME}.imported.md5"     # último MD5 ya importado
LAST_TS_FILE="${STATE_DIR}/${BASENAME}.last_download_epoch" # última descarga exitosa (epoch)

# Throttle: respetar ventana mínima entre descargas (en días)
THROTTLE_DAYS="${OSM_DOWNLOAD_THROTTLE_DAYS:-0}"
if [ "${THROTTLE_DAYS}" -gt 0 ] && { [ -s "$PBF_FILE" ] || [ -s "$STAMP_IMPORTED" ]; }; then
  if [ -f "$LAST_TS_FILE" ]; then
    now="$(date +%s)"
    last="$(cat "$LAST_TS_FILE" 2>/dev/null || echo 0)"
    max_age=$(( THROTTLE_DAYS * 86400 ))
    age=$(( now - last ))
    if [ "$age" -lt "$max_age" ]; then
      remaining=$(( (max_age - age + 86399) / 86400 )) # redondeo hacia arriba en días
      # Nota: si no querés que "importe" todos los días, esta salida 0 evita cualquier trabajo.
      echo "[Update] Throttled: última descarga el $(date -d "@$last" +%F). Próxima permitida desde $(date -d "@$((last+max_age))" +%F) (~${remaining} días)."
      exit 0
    fi
  fi
fi

TMP_MD5="$(mktemp)"; trap 'rm -f "$TMP_MD5"' EXIT
echo "[Update] Consultando MD5 remoto: $MD5_URL"
curl --retry 3 --retry-all-errors -fsSL "$MD5_URL" -o "$TMP_MD5"
REMOTE_MD5="$(awk '{print $1}' "$TMP_MD5")"

if [[ -f "$STAMP_IMPORTED" ]] && [[ "$(cat "$STAMP_IMPORTED")" == "$REMOTE_MD5" ]]; then
  echo "[Update] Ya importado este MD5 ($REMOTE_MD5). No se descarga ni se extrae."
  exit 0
fi

# Descarga (si hace falta) y verificación
"$(dirname "$0")/download_geofabrik.sh" "$DATA_DIR" "$REGION"

# Confirmar PBF descargado
PBF_FILE="${DATA_DIR}/$(basename "${REGION}.osm.pbf")"
if [ ! -s "$PBF_FILE" ]; then
  echo "ERROR: No se encontró PBF esperado en $PBF_FILE"
  exit 1
fi

# Extracción / exportación (solo si cambia MD5)
"$(dirname "$0")/extract_pois.sh" "$PBF_FILE" "$OUT_DIR"

# Marcar timestamp de descarga/actualización exitosa
date +%s > "$LAST_TS_FILE"