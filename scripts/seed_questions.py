#!/usr/bin/env python3
"""
seed_questions.py — Create Metabase questions and place them on a dashboard.
Usage: python3 seed_questions.py <MB_HOST> <MB_TOKEN> <DB_ID> <DASH_ID>
"""
import requests, json, sys

MB_HOST = sys.argv[1]
TOKEN   = sys.argv[2]
DB_ID   = int(sys.argv[3])
DASH_ID = int(sys.argv[4])

BASE = f"{MB_HOST}/api"
HEADERS = {"X-Metabase-Session": TOKEN, "Content-Type": "application/json"}


def api(method, path, data=None):
    r = getattr(requests, method)(f"{BASE}{path}", headers=HEADERS, json=data)
    if r.status_code >= 400:
        print(f"  ERROR {r.status_code} {method.upper()} {path}: {r.text[:300]}")
        sys.exit(1)
    try:
        return r.json()
    except Exception:
        return {}


QUESTIONS = [
    {
        "name": "Distribución de contactos por estado",
        "sql": 'SELECT contact_state AS "Estado", COUNT(*) AS "Cantidad" FROM owner_contact_facts GROUP BY contact_state ORDER BY Cantidad DESC',
        "display": "pie",
        "viz": {},
    },
    {
        "name": "Contactos recibidos por mes",
        "sql": "SELECT DATE_FORMAT(contact_issue_date, '%Y-%m') AS \"Mes\", COUNT(*) AS \"Contactos\" FROM owner_contact_facts GROUP BY Mes ORDER BY Mes",
        "display": "line",
        "viz": {"graph.x_axis.scale": "ordinal", "graph.dimensions": ["Mes"], "graph.metrics": ["Contactos"]},
    },
    {
        "name": "Tiempo promedio de respuesta por propietario",
        "sql": "SELECT CONCAT(owner_first_name, ' ', owner_last_name) AS \"Propietario\", ROUND(AVG(contact_response_minutes), 0) AS \"Prom. minutos respuesta\", COUNT(*) AS \"Total contactos\" FROM owner_contact_facts WHERE contact_response_minutes IS NOT NULL GROUP BY owner_id, owner_first_name, owner_last_name ORDER BY `Prom. minutos respuesta` ASC",
        "display": "bar",
        "viz": {"graph.x_axis.scale": "ordinal", "graph.dimensions": ["Propietario"], "graph.metrics": ["Prom. minutos respuesta"]},
    },
    {
        "name": "Tasa de conversión: contactos → alquileres",
        "sql": "SELECT property_title AS \"Propiedad\", property_type AS \"Tipo\", COUNT(DISTINCT contact_id) AS \"Contactos\", COUNT(DISTINCT rent_id) AS \"Alquileres\", ROUND(COUNT(DISTINCT rent_id) * 100.0 / NULLIF(COUNT(DISTINCT contact_id), 0), 1) AS \"% Conversión\" FROM owner_contact_facts GROUP BY property_id, property_title, property_type ORDER BY `% Conversión` DESC",
        "display": "table",
        "viz": {},
    },
    {
        "name": "Ingreso mensual total (alquileres activos)",
        "sql": "SELECT COALESCE(SUM(rent_price), 0) AS \"Ingreso mensual total\" FROM owner_contact_facts WHERE rent_state = 'ACTIVE'",
        "display": "scalar",
        "viz": {"scalar.field": "Ingreso mensual total"},
    },
    {
        "name": "Distribución de alquileres por índice de ajuste",
        "sql": "SELECT rent_raise_index AS \"Índice de ajuste\", COUNT(*) AS \"Cantidad\" FROM owner_contact_facts WHERE rent_id IS NOT NULL GROUP BY rent_raise_index ORDER BY Cantidad DESC",
        "display": "pie",
        "viz": {},
    },
    {
        "name": "Top 10 propiedades con más contactos",
        "sql": "SELECT property_title AS \"Propiedad\", property_address AS \"Dirección\", COUNT(DISTINCT contact_id) AS \"Contactos\" FROM owner_contact_facts GROUP BY property_id, property_title, property_address ORDER BY Contactos DESC LIMIT 10",
        "display": "bar",
        "viz": {"graph.x_axis.scale": "ordinal", "graph.dimensions": ["Propiedad"], "graph.metrics": ["Contactos"]},
    },
    {
        "name": "Alquileres que vencen en los próximos 90 días",
        "sql": "SELECT property_title AS \"Propiedad\", CONCAT(issuer_first_name, ' ', issuer_last_name) AS \"Inquilino\", rent_start_date AS \"Inicio\", rent_due_date AS \"Vencimiento\", DATEDIFF(rent_due_date, CURDATE()) AS \"Días restantes\", rent_price AS \"Precio\" FROM owner_contact_facts WHERE rent_state = 'ACTIVE' AND rent_due_date BETWEEN CURDATE() AND DATE_ADD(CURDATE(), INTERVAL 90 DAY) ORDER BY rent_due_date ASC",
        "display": "table",
        "viz": {},
    },
    {
        "name": "Precio promedio de alquiler por tipo de propiedad",
        "sql": "SELECT property_type AS \"Tipo\", ROUND(AVG(rent_price), 2) AS \"Precio prom. alquiler\", COUNT(DISTINCT rent_id) AS \"Cant. alquileres\" FROM owner_contact_facts WHERE rent_id IS NOT NULL GROUP BY property_type",
        "display": "bar",
        "viz": {"graph.x_axis.scale": "ordinal", "graph.dimensions": ["Tipo"], "graph.metrics": ["Precio prom. alquiler"]},
    },
    {
        "name": "Contactos sin responder (más de 48 hs)",
        "sql": "SELECT property_title AS \"Propiedad\", CONCAT(owner_first_name, ' ', owner_last_name) AS \"Propietario\", CONCAT(issuer_first_name, ' ', issuer_last_name) AS \"Interesado\", contact_issue_date AS \"Fecha contacto\", TIMESTAMPDIFF(HOUR, contact_issue_date, NOW()) AS \"Horas sin respuesta\" FROM owner_contact_facts WHERE contact_state = 'CREATED' AND contact_answer_date IS NULL AND TIMESTAMPDIFF(HOUR, contact_issue_date, NOW()) > 48 ORDER BY contact_issue_date ASC",
        "display": "table",
        "viz": {},
    },
    {
        "name": "Duración promedio de contratos por índice de ajuste",
        "sql": "SELECT rent_raise_index AS \"Índice\", ROUND(AVG(rent_duration_months), 1) AS \"Duración prom. (meses)\", ROUND(AVG(rent_price), 0) AS \"Precio prom.\" FROM owner_contact_facts WHERE rent_id IS NOT NULL GROUP BY rent_raise_index ORDER BY `Duración prom. (meses)` DESC",
        "display": "bar",
        "viz": {"graph.x_axis.scale": "ordinal", "graph.dimensions": ["Índice"], "graph.metrics": ["Duración prom. (meses)", "Precio prom."]},
    },
    {
        "name": "Alquileres cancelados con motivo",
        "sql": "SELECT property_title AS \"Propiedad\", CONCAT(issuer_first_name, ' ', issuer_last_name) AS \"Inquilino\", rent_start_date AS \"Inicio\", rent_cancellation_date AS \"Fecha cancelación\", rent_cancellation_reason AS \"Motivo\" FROM owner_contact_facts WHERE rent_state = 'CANCELLED' ORDER BY rent_cancellation_date DESC",
        "display": "table",
        "viz": {},
    },
    {
        "name": "Resumen general (KPIs)",
        "sql": "SELECT COUNT(DISTINCT property_id) AS \"Propiedades activas\", COUNT(DISTINCT contact_id) AS \"Total contactos\", COUNT(DISTINCT CASE WHEN rent_state = 'ACTIVE' THEN rent_id END) AS \"Alquileres activos\", ROUND(AVG(contact_response_minutes), 0) AS \"Prom. resp. (min)\", COALESCE(SUM(CASE WHEN rent_state = 'ACTIVE' THEN rent_price END), 0) AS \"Ingreso mensual ($)\"FROM owner_contact_facts",
        "display": "table",
        "viz": {},
    },
]

# Layout: (question_index, col, row, size_x, size_y)
LAYOUT = [
    (12, 0, 0, 24, 4),     # Resumen general (KPIs)
    (4,  0, 4, 8, 4),      # Ingreso mensual scalar
    (0,  8, 4, 8, 4),      # Distribución contactos pie
    (5, 16, 4, 8, 4),      # Distribución alquileres pie
    (1,  0, 8, 12, 6),     # Contactos por mes line
    (2, 12, 8, 12, 6),     # Tiempo resp propietario bar
    (6,  0, 14, 12, 7),    # Top 10 propiedades bar
    (3, 12, 14, 12, 7),    # Conversión table
    (8,  0, 21, 12, 6),    # Precio prom tipo bar
    (10,12, 21, 12, 6),    # Duración contratos bar
    (9,  0, 27, 24, 6),    # Contactos sin responder table
    (7,  0, 33, 24, 6),    # Alquileres vencen 90 días table
    (11, 0, 39, 24, 6),    # Alquileres cancelados table
]


def main():
    # Create cards
    card_ids = []
    for q in QUESTIONS:
        card = api("post", "/card", {
            "name": q["name"],
            "dataset_query": {
                "type": "native",
                "native": {"query": q["sql"]},
                "database": DB_ID,
            },
            "display": q["display"],
            "visualization_settings": q["viz"],
            "collection_id": None,
        })
        card_ids.append(card["id"])
        print(f"  Created card {card['id']}: {q['name']} ({q['display']})")

    # Build dashcards
    dashcards = []
    for qi, col, row, sx, sy in LAYOUT:
        dashcards.append({
            "id": -1 - len(dashcards),
            "card_id": card_ids[qi],
            "col": col,
            "row": row,
            "size_x": sx,
            "size_y": sy,
            "parameter_mappings": [],
            "visualization_settings": {},
            "series": [],
        })

    api("put", f"/dashboard/{DASH_ID}", {
        "dashcards": dashcards,
        "name": "Propinq - Reportes",
    })
    print(f"  Dashboard {DASH_ID} updated with {len(dashcards)} cards.")


if __name__ == "__main__":
    main()
