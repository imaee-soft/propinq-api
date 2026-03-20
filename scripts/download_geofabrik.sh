#!/usr/bin/env bash
set -euo pipefail

# Uso:
#   geofabrik-download.sh [OUT_DIR] [REGION]
# Ejemplo:
#   geofabrik-download.sh data south-america/argentina-latest
#
# Comportamiento:
# - Si el MD5 remoto coincide con el ÚLTIMO MD5 IMPORTADO (guardado en $STATE_DIR),
#   no descarga nada y sale con código 0 (nada que hacer).
# - Si el MD5 remoto cambió, descarga el .osm.pbf (a OUT_DIR) y su .md5.

OUT_DIR="${1:-data}"
REGION="${2:-south-america/argentina-latest}"

mkdir -p "$OUT_DIR"

BASE="https://download.geofabrik.de"
PBF_URL="${BASE}/${REGION}.osm.pbf"
MD5_URL="${PBF_URL}.md5"
PBF_FILE="${OUT_DIR}/$(basename "${REGION}.osm.pbf")"
MD5_FILE="${PBF_FILE}.md5"

# Estado persistente (fuera de data)
STATE_DIR="${OSM_STATE_DIR:-${XDG_STATE_HOME:-$HOME/.local/state}/propinq}"
mkdir -p "$STATE_DIR"
BASENAME="$(basename "$REGION")"             # argentina-latest
STAMP_IMPORTED="${STATE_DIR}/${BASENAME}.imported.md5"  # guarda último MD5 ya importado

TMP_MD5="$(mktemp)"
trap 'rm -f "$TMP_MD5"' EXIT

echo "[Geofabrik] Consultando MD5 remoto: ${MD5_URL}"
curl --retry 3 --retry-all-errors -fsSL "$MD5_URL" -o "$TMP_MD5"

REMOTE_MD5="$(awk '{print $1}' "$TMP_MD5")"

# Si ya importaste este MD5, no bajes nada aunque data esté vacío
if [[ -f "$STAMP_IMPORTED" ]]; then
  LAST_IMPORTED="$(cat "$STAMP_IMPORTED" || true)"
  if [[ "$LAST_IMPORTED" == "$REMOTE_MD5" ]]; then
    echo "[Geofabrik] Ya importado este MD5 (${REMOTE_MD5}). Nada que hacer."
    exit 0
  fi
fi

# Si existe MD5 local y coincide con remoto, tampoco hace falta re-descargar el PBF
if [[ -f "$MD5_FILE" ]]; then
  LOCAL_MD5="$(awk '{print $1}' "$MD5_FILE")"
  if [[ "$LOCAL_MD5" == "$REMOTE_MD5" && -f "$PBF_FILE" ]]; then
    echo "[Geofabrik] Archivo ya presente y coincide con MD5 remoto. Nada que descargar."
    exit 0
  fi
fi

echo "[Geofabrik] Descargando ${PBF_URL} ..."
# descarga segura: escribe a .part y luego renombra
curl -A "propinq-import/1.0" --retry 3 --retry-all-errors -fSL -o "${PBF_FILE}.part" "$PBF_URL"
mv -f "${PBF_FILE}.part" "$PBF_FILE"

# Sanidad: abortar si pesa demasiado poco (evita HTML/errores)
MIN_BYTES=$((50 * 1024 * 1024))  # 50MB
ACTUAL_BYTES=$(stat -c%s "$PBF_FILE" || echo 0)
if (( ACTUAL_BYTES < MIN_BYTES )); then
  echo "ERROR: descarga sospechosamente pequeña ($ACTUAL_BYTES bytes). Abortando."
  exit 1
fi

# Guardar MD5 remoto junto al PBF
cp -f "$TMP_MD5" "$MD5_FILE"

echo "[Geofabrik] Verificando checksum..."
( cd "$(dirname "$PBF_FILE")" && md5sum -c "$(basename "$MD5_FILE")" )

echo "[Geofabrik] OK -> $PBF_FILE"