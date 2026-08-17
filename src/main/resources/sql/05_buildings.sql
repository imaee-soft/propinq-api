-- Datos demo actuales (fuente: propinq-infra/scripts/seed-demo-properties.sql).
-- Alineado con CT102 / www.propinq.online.

-- Buildings seed
INSERT IGNORE INTO buildings (
    building_id, name, description, address, latitude, longitude,
    user_user_id, building_type, deleted, created_at
) VALUES
(
    UNHEX(REPLACE('b1000000-0000-4000-8000-000000000001', '-', '')),
    'Torre Villa María Centro',
    'Edificio residencial de prueba en Villa María, Córdoba',
    'Bv. España 1000, Villa María, Córdoba',
    -32.4094, -63.2432,
    UNHEX('11111111111111111111111111111111'),
    'EDIFICIO', 0, NOW(6)
),
(
    UNHEX(REPLACE('b1000000-0000-4000-8000-000000000002', '-', '')),
    'Edificio Nueva Córdoba',
    'Torre con departamentos en Nueva Córdoba, Córdoba Capital',
    'Av. Hipólito Yrigoyen 450, Córdoba Capital',
    -31.4265, -64.1865,
    UNHEX('11111111111111111111111111111111'),
    'EDIFICIO', 0, NOW(6)
),
(
    UNHEX(REPLACE('b1000000-0000-4000-8000-000000000003', '-', '')),
    'Torre Palermo Soho',
    'Edificio con amenities en Palermo, CABA',
    'Honduras 4800, Palermo, CABA',
    -34.5889, -58.4258,
    UNHEX('11111111111111111111111111111111'),
    'EDIFICIO', 0, NOW(6)
);

