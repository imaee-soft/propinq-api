#!/usr/bin/env python3
"""
seed_questions.py — Create or replace the compact PropInq reports dashboard in Metabase.
Usage: python3 seed_questions.py <MB_HOST> <MB_TOKEN> <DB_ID> [<DASH_ID>]
"""
import json
import os
import sys

import requests

MB_HOST = sys.argv[1]
TOKEN = sys.argv[2]
DB_ID = int(sys.argv[3])
DASH_ID_ARG = int(sys.argv[4]) if len(sys.argv) > 4 and sys.argv[4] else None

BASE = f"{MB_HOST}/api"
HEADERS = {"X-Metabase-Session": TOKEN, "Content-Type": "application/json"}

DASHBOARD_NAME = "Propinq - Reportes v2"
EMAIL_PARAM_ID = "owner-email-filter"

EMAIL_TEMPLATE_TAG = {
    "email": {
        "id": "owner_email",
        "name": "email",
        "display-name": "Email propietario",
        "type": "text",
        "required": True,
    }
}


def api(method, path, data=None):
    response = getattr(requests, method)(f"{BASE}{path}", headers=HEADERS, json=data)
    if response.status_code >= 400:
        print(f"  ERROR {response.status_code} {method.upper()} {path}: {response.text[:500]}")
        sys.exit(1)
    try:
        return response.json()
    except Exception:
        return {}


def native_query(sql: str) -> dict:
    return {
        "type": "native",
        "native": {
            "query": sql,
            "template-tags": EMAIL_TEMPLATE_TAG,
        },
        "database": DB_ID,
    }


def scalar_viz(field: str) -> dict:
    return {
        "scalar.field": field,
        "scalar.compact_primary_number": False,
    }


QUESTIONS = [
    {
        "name": "Propiedades activas",
        "sql": """
SELECT COUNT(DISTINCT property_id) AS "Propiedades activas"
FROM owner_contact_facts
WHERE owner_email = {{email}}
""".strip(),
        "display": "scalar",
        "viz": scalar_viz("Propiedades activas"),
    },
    {
        "name": "Total negociaciones",
        "sql": """
SELECT COUNT(DISTINCT contact_id) AS "Total negociaciones"
FROM owner_contact_facts
WHERE owner_email = {{email}}
""".strip(),
        "display": "scalar",
        "viz": scalar_viz("Total negociaciones"),
    },
    {
        "name": "Alquileres activos",
        "sql": """
SELECT COUNT(DISTINCT CASE WHEN rent_state = 'Activo' THEN rent_id END) AS "Alquileres activos"
FROM owner_contact_facts
WHERE owner_email = {{email}}
""".strip(),
        "display": "scalar",
        "viz": scalar_viz("Alquileres activos"),
    },
    {
        "name": "Prom. respuesta (min)",
        "sql": """
SELECT ROUND(AVG(contact_response_minutes), 0) AS "Prom. respuesta (min)"
FROM owner_contact_facts
WHERE owner_email = {{email}}
  AND contact_response_minutes IS NOT NULL
""".strip(),
        "display": "scalar",
        "viz": scalar_viz("Prom. respuesta (min)"),
    },
    {
        "name": "Ingreso mensual total (alquileres activos)",
        "sql": """
SELECT COALESCE(SUM(rent_price), 0) AS "Ingreso mensual total"
FROM owner_contact_facts
WHERE rent_state = 'Activo'
  AND owner_email = {{email}}
""".strip(),
        "display": "scalar",
        "viz": scalar_viz("Ingreso mensual total"),
    },
    {
        "name": "Negociaciones recibidas por mes",
        "sql": """
SELECT
    DATE_FORMAT(contact_issue_date, '%Y-%m') AS "Mes",
    COUNT(*) AS "Negociaciones"
FROM owner_contact_facts
WHERE owner_email = {{email}}
GROUP BY Mes
ORDER BY Mes
""".strip(),
        "display": "line",
        "viz": {
            "graph.x_axis.scale": "ordinal",
            "graph.dimensions": ["Mes"],
            "graph.metrics": ["Negociaciones"],
        },
    },
    {
        "name": "Top 10 propiedades con más negociaciones",
        "sql": """
SELECT
    property_title AS "Propiedad",
    COUNT(DISTINCT contact_id) AS "Negociaciones"
FROM owner_contact_facts
WHERE owner_email = {{email}}
GROUP BY property_id, property_title
ORDER BY Negociaciones DESC
LIMIT 10
""".strip(),
        "display": "bar",
        "viz": {
            "graph.x_axis.scale": "ordinal",
            "graph.dimensions": ["Propiedad"],
            "graph.metrics": ["Negociaciones"],
        },
    },
    {
        "name": "Negociaciones sin responder",
        "sql": """
SELECT
    property_title AS "Propiedad",
    CONCAT(issuer_first_name, ' ', issuer_last_name) AS "Interesado",
    contact_issue_date AS "Fecha",
    TIMESTAMPDIFF(HOUR, contact_issue_date, NOW()) AS "Horas"
FROM owner_contact_facts
WHERE owner_email = {{email}}
  AND contact_state = 'Creado'
  AND contact_answer_date IS NULL
ORDER BY contact_issue_date ASC
""".strip(),
        "display": "table",
        "viz": {},
    },
    {
        "name": "Alquileres que vencen en los próximos 90 días",
        "sql": """
SELECT
    property_title AS "Propiedad",
    CONCAT(issuer_first_name, ' ', issuer_last_name) AS "Inquilino",
    rent_due_date AS "Vencimiento",
    DATEDIFF(rent_due_date, CURDATE()) AS "Días",
    rent_price AS "Precio"
FROM owner_contact_facts
WHERE owner_email = {{email}}
  AND rent_state = 'Activo'
  AND rent_due_date BETWEEN CURDATE() AND DATE_ADD(CURDATE(), INTERVAL 90 DAY)
ORDER BY rent_due_date ASC
""".strip(),
        "display": "table",
        "viz": {},
    },
    {
        "name": "Top 5 total de favoritos por propiedad",
        "sql": """
SELECT
    p.title AS "Propiedad",
    COUNT(*) AS "Favoritos"
FROM favorites f
JOIN properties p ON f.property_id = p.property_id
JOIN users o ON p.user_user_id = o.user_id
WHERE f.property_id IS NOT NULL
  AND o.email = {{email}}
GROUP BY p.property_id, p.title
ORDER BY Favoritos DESC
LIMIT 5
""".strip(),
        "display": "bar",
        "viz": {
            "graph.x_axis.scale": "ordinal",
            "graph.dimensions": ["Propiedad"],
            "graph.metrics": ["Favoritos"],
        },
    },
]

# (question_index, col, row, size_x, size_y)
LAYOUT = [
    (0, 0, 0, 6, 2),     # Propiedades activas
    (1, 6, 0, 6, 2),     # Total negociaciones
    (2, 12, 0, 6, 2),    # Alquileres activos
    (3, 18, 0, 6, 2),    # Prom. respuesta
    (4, 0, 5, 6, 5),     # Ingreso mensual
    (5, 6, 5, 18, 5),    # Negociaciones por mes
    (6, 0, 10, 12, 6),   # Top 10 negociaciones
    (9, 12, 10, 12, 6),  # Top 5 favoritos
    (7, 0, 16, 12, 6),   # Sin responder
    (8, 12, 16, 12, 6),  # Vencen 90 días
]


def find_or_create_dashboard() -> int:
    if DASH_ID_ARG:
        return DASH_ID_ARG

    dashboards = api("get", "/dashboard")
    for dashboard in dashboards:
        if dashboard.get("name") == DASHBOARD_NAME:
            print(f"  Reusing dashboard {dashboard['id']}: {DASHBOARD_NAME}")
            return dashboard["id"]

    created = api("post", "/dashboard", {
        "name": DASHBOARD_NAME,
        "collection_id": None,
    })
    print(f"  Created dashboard {created['id']}: {DASHBOARD_NAME}")
    return created["id"]


def create_cards() -> list[int]:
    card_ids = []
    for question in QUESTIONS:
        card = api("post", "/card", {
            "name": question["name"],
            "dataset_query": native_query(question["sql"]),
            "display": question["display"],
            "visualization_settings": question["viz"],
            "collection_id": None,
        })
        card_ids.append(card["id"])
        print(f"  Created card {card['id']}: {question['name']} ({question['display']})")
    return card_ids


def build_dashcards(card_ids: list[int]) -> list[dict]:
    dashcards = []
    for index, (question_index, col, row, size_x, size_y) in enumerate(LAYOUT):
        card_id = card_ids[question_index]
        dashcards.append({
            "id": -1 - index,
            "card_id": card_id,
            "col": col,
            "row": row,
            "size_x": size_x,
            "size_y": size_y,
            "parameter_mappings": [{
                "parameter_id": EMAIL_PARAM_ID,
                "card_id": card_id,
                "target": ["variable", ["template-tag", "email"]],
            }],
            "visualization_settings": {},
            "series": [],
        })
    return dashcards


def write_dashboard_id(dash_id: int) -> None:
    script_dir = os.path.dirname(os.path.abspath(__file__))
    output_path = os.path.join(script_dir, ".dashboard-id")
    with open(output_path, "w", encoding="utf-8") as handle:
        handle.write(str(dash_id))
    print(f"  Dashboard ID written to {output_path}")


def main():
    dash_id = find_or_create_dashboard()
    card_ids = create_cards()
    dashcards = build_dashcards(card_ids)

    api("put", f"/dashboard/{dash_id}", {
        "name": DASHBOARD_NAME,
        "width": "full",
        "enable_embedding": True,
        "embedding_params": {"email": "locked"},
        "parameters": [{
            "id": EMAIL_PARAM_ID,
            "name": "email",
            "slug": "email",
            "type": "string/=",
            "sectionId": "string",
        }],
        "dashcards": dashcards,
    })

    write_dashboard_id(dash_id)
    print(f"  Dashboard {dash_id} updated with {len(dashcards)} cards.")


if __name__ == "__main__":
    main()
