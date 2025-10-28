-- Buildings seed
INSERT IGNORE INTO buildings (
    building_id,
    name,
    description,
    address,
    latitude,
    longitude,
    user_user_id,
    building_type,
    deleted
) VALUES (
    UNHEX('123e4567e89b12d3a456426614174000'),
    'Torre Villa María',
    'Edificio de prueba en Villa María, Córdoba, para desarrollo frontend',
    'Bv. España 1000, Villa María, Córdoba',
    -32.4094,
    -63.2432,
    UNHEX('11111111111111111111111111111111'),
    'EDIFICIO',
    0
);
