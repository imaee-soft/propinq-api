#!/usr/bin/env bash
set -euo pipefail

# Uso:
#   extract-pois.sh [PBF_IN] [OUT_DIR]
# Ejemplo:
#   extract-pois.sh data/argentina-latest.osm.pbf out
#
# Comportamiento:
# - Lee el MD5 del PBF. Si coincide con el "último importado" en $STATE_DIR,
#   no ejecuta la importación. Si difiere, filtra/exporta y actualiza el stamp.

PBF_IN="${1:-data/argentina-latest.osm.pbf}"
OUT_DIR="${2:-out}"
mkdir -p "$OUT_DIR"

if ! command -v osmium >/dev/null 2>&1; then
  echo "ERROR: osmium-tool no está instalado."
  exit 1
fi

# Estado persistente
STATE_DIR="${OSM_STATE_DIR:-${XDG_STATE_HOME:-$HOME/.local/state}/propinq}"
mkdir -p "$STATE_DIR"
BASENAME="$(basename "$PBF_IN" .osm.pbf)"                  # argentina-latest
STAMP_IMPORTED="${STATE_DIR}/${BASENAME}.imported.md5"     # guarda último MD5 ya importado

if [ ! -f "$PBF_IN" ]; then
  echo "ERROR: No existe $PBF_IN"
  exit 1
fi

# MD5 actual del PBF (coherente con geofabrik)
NEW_MD5="$(md5sum "$PBF_IN" | awk '{print $1}')"
OLD_MD5=""
[[ -f "$STAMP_IMPORTED" ]] && OLD_MD5="$(cat "$STAMP_IMPORTED")"

echo "[DEBUG] PBF_IN: $PBF_IN"
echo "[DEBUG] STATE_DIR: $STATE_DIR"
echo "[DEBUG] STAMP_IMPORTED: $STAMP_IMPORTED"
ls -l "$STATE_DIR"
echo "[DEBUG] NEW_MD5: $NEW_MD5"
echo "[DEBUG] OLD_MD5: $OLD_MD5"
if [[ "$NEW_MD5" == "$OLD_MD5" ]]; then
  echo "No hay cambios (MD5 ya importado: $NEW_MD5). No se ejecuta importación."
  exit 0
fi

read -r -d '' FILTERS <<'EOF' || true
nwr/amenity
nwr/shop
nwr/tourism
nwr/leisure
nwr/historic
nwr/man_made
nwr/natural
nwr/sport
nwr/craft
nwr/office
nwr/emergency
nwr/healthcare
nwr/public_transport
nwr/railway
nwr/aeroway
nwr/aerialway
nwr/power
nwr/waterway
nwr/highway=bus_stop
nwr/highway=platform
nwr/highway=rest_area
EOF

ARGS=()
while IFS= read -r line; do
  [[ -z "$line" ]] && continue
  ARGS+=( "$line" )
done <<< "$FILTERS"

POI_PBF="${OUT_DIR}/pois.pbf"
POI_GJ="${OUT_DIR}/pois.geojsonseq"

echo "[1/2] Filtrando POIs -> ${POI_PBF}"
osmium tags-filter "$PBF_IN" "${ARGS[@]}" -o "$POI_PBF" -O

echo "[2/2] Exportando a GeoJSON -> ${POI_GJ}"
if osmium export --help 2>&1 | grep -q -- '--geometry='; then
  osmium export "$POI_PBF" \
    -f geojsonseq \
    --geometry=centroid \
    -o "$POI_GJ" \
    -O
else
  osmium export "$POI_PBF" \
    -f geojsonseq \
    -o "$POI_GJ" \
    -O
fi

if [ ! -s "$POI_GJ" ]; then
  echo "ERROR: $POI_GJ no fue creado o está vacío"
  exit 1
fi

# Actualizar estado persistente solo si todo salió bien
echo "$NEW_MD5" > "$STAMP_IMPORTED"
echo "Importación finalizada con nuevos datos. MD5 importado: $NEW_MD5"