#!/usr/bin/env bash
# ──────────────────────────────────────────────────────────────
# metabase_seed.sh
# Seed Metabase with owner_contact_facts and the compact v2 dashboard.
# Creates or replaces "Propinq - Reportes v2" (7 cards, owner filter).
# ──────────────────────────────────────────────────────────────
set -euo pipefail

MB_HOST="${MB_HOST:-http://metabase:3000}"
MB_ADMIN_EMAIL="${MB_ADMIN_EMAIL:?Set MB_ADMIN_EMAIL}"
MB_ADMIN_PASSWORD="${MB_ADMIN_PASSWORD:?Set MB_ADMIN_PASSWORD}"

MYSQL_HOST="${MYSQL_HOST:-mysql-db}"
MYSQL_PORT="${MYSQL_INTERNAL_PORT:-3306}"
MYSQL_USER="${MYSQL_USERNAME:-root}"
MYSQL_PASS="${MYSQL_PASSWORD:?Set MYSQL_PASSWORD}"
MYSQL_DB="${MYSQL_DATABASE:-propinq}"

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
VIEW_SQL="$SCRIPT_DIR/create_view.sql"

echo "[metabase-seed] Waiting for Metabase at $MB_HOST ..."
until curl -sf "$MB_HOST/api/health" > /dev/null 2>&1; do
  sleep 5
done
echo "[metabase-seed] Metabase is up."

echo "[metabase-seed] Ensuring owner_contact_facts view exists..."
mysql --skip-ssl -h "$MYSQL_HOST" -P "$MYSQL_PORT" -u "$MYSQL_USER" -p"$MYSQL_PASS" "$MYSQL_DB" < "$VIEW_SQL"
echo "[metabase-seed] View ready."

MB_TOKEN=$(curl -sf -X POST "$MB_HOST/api/session" \
  -H "Content-Type: application/json" \
  -d "{\"username\":\"$MB_ADMIN_EMAIL\",\"password\":\"$MB_ADMIN_PASSWORD\"}" \
  | python3 -c "import json,sys; print(json.load(sys.stdin)['id'])")
echo "[metabase-seed] Authenticated."

DB_ID=$(curl -sf -H "X-Metabase-Session: $MB_TOKEN" "$MB_HOST/api/database" \
  | python3 -c "
import json, sys
for db in json.load(sys.stdin)['data']:
    if db['name'].lower() == 'propinq':
        print(db['id']); break
")
echo "[metabase-seed] Database ID: $DB_ID"

curl -sf -X POST "$MB_HOST/api/database/$DB_ID/sync_schema" \
  -H "X-Metabase-Session: $MB_TOKEN" > /dev/null
echo "[metabase-seed] Schema sync triggered."
sleep 10

echo "[metabase-seed] Creating compact dashboard..."
python3 "$SCRIPT_DIR/seed_questions.py" "$MB_HOST" "$MB_TOKEN" "$DB_ID"

DASH_ID=$(cat "$SCRIPT_DIR/.dashboard-id")
echo "[metabase-seed] Done! Dashboard ready at $MB_HOST/dashboard/$DASH_ID"
