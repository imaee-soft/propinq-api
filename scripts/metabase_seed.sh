#!/usr/bin/env bash
# ──────────────────────────────────────────────────────────────
# metabase_seed.sh
# Seed Metabase with the owner_contact_facts view and dashboard.
# Runs once: creates the MySQL view and imports questions+dashboard
# via the Metabase API.  Idempotent — skips if dashboard already has cards.
# ──────────────────────────────────────────────────────────────
set -euo pipefail

# ── Config (from env or defaults) ──
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

# ── Wait for Metabase to be ready ──
echo "[metabase-seed] Waiting for Metabase at $MB_HOST ..."
until curl -sf "$MB_HOST/api/health" > /dev/null 2>&1; do
  sleep 5
done
echo "[metabase-seed] Metabase is up."

# ── 1. Create / replace the MySQL view ──
echo "[metabase-seed] Ensuring owner_contact_facts view exists..."
mysql -h "$MYSQL_HOST" -P "$MYSQL_PORT" -u "$MYSQL_USER" -p"$MYSQL_PASS" "$MYSQL_DB" < "$VIEW_SQL"
echo "[metabase-seed] View ready."

# ── 2. Authenticate with Metabase ──
MB_TOKEN=$(curl -sf -X POST "$MB_HOST/api/session" \
  -H "Content-Type: application/json" \
  -d "{\"username\":\"$MB_ADMIN_EMAIL\",\"password\":\"$MB_ADMIN_PASSWORD\"}" \
  | python3 -c "import json,sys; print(json.load(sys.stdin)['id'])")
echo "[metabase-seed] Authenticated."

# ── 3. Find propinq database ID ──
DB_ID=$(curl -sf -H "X-Metabase-Session: $MB_TOKEN" "$MB_HOST/api/database" \
  | python3 -c "
import json, sys
for db in json.load(sys.stdin)['data']:
    if db['name'].lower() == 'propinq':
        print(db['id']); break
")
echo "[metabase-seed] Database ID: $DB_ID"

# ── 4. Trigger a metadata sync so the view is visible ──
curl -sf -X POST "$MB_HOST/api/database/$DB_ID/sync_schema" \
  -H "X-Metabase-Session: $MB_TOKEN" > /dev/null
echo "[metabase-seed] Schema sync triggered."
sleep 10  # give it time

# ── 5. Check if dashboard already has cards (idempotent) ──
EXISTING=$(curl -sf -H "X-Metabase-Session: $MB_TOKEN" "$MB_HOST/api/dashboard" \
  | python3 -c "
import json, sys
dashboards = json.load(sys.stdin)
for d in dashboards:
    if 'Propinq' in d.get('name', ''):
        print(d['id']); sys.exit(0)
print('')
")

if [ -n "$EXISTING" ]; then
  CARD_COUNT=$(curl -sf -H "X-Metabase-Session: $MB_TOKEN" "$MB_HOST/api/dashboard/$EXISTING" \
    | python3 -c "import json,sys; print(len(json.load(sys.stdin).get('dashcards',[])))")
  if [ "$CARD_COUNT" -gt 0 ]; then
    echo "[metabase-seed] Dashboard already exists with $CARD_COUNT cards. Skipping."
    exit 0
  fi
  DASH_ID=$EXISTING
else
  # Create the dashboard
  DASH_ID=$(curl -sf -X POST "$MB_HOST/api/dashboard" \
    -H "X-Metabase-Session: $MB_TOKEN" \
    -H "Content-Type: application/json" \
    -d '{"name":"Propinq - Reportes","collection_id":null}' \
    | python3 -c "import json,sys; print(json.load(sys.stdin)['id'])")
fi
echo "[metabase-seed] Dashboard ID: $DASH_ID"

# ── 6. Create all questions ──
echo "[metabase-seed] Creating questions..."
python3 "$SCRIPT_DIR/seed_questions.py" "$MB_HOST" "$MB_TOKEN" "$DB_ID" "$DASH_ID"

echo "[metabase-seed] Done! Dashboard ready at $MB_HOST/dashboard/$DASH_ID"
