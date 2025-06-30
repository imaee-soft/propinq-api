-- Insertar usuario de prueba
INSERT INTO users (
    user_id,
    username,
    password,
    first_name,
    last_name,
    email,
    phone_number,
    role,
    deleted,
    activated,
    address
) VALUES (
    UNHEX('11111111111111111111111111111111'),
    'demo_owner',
    'demo_password',
    'Juan',
    'Pérez',
    'juan.perez@ejemplo.com',
    '+5493534123456',
    'OWNER',
    0,
    1,
    'Sin dirección'
);

-- Insertar tipo de edificio de prueba
INSERT INTO building_types (
    building_type_id,
    name,
    deleted
) VALUES (
    UNHEX('22222222222222222222222222222222'),
    'RESIDENTIAL',
    0
);

-- Insertar tipo de propiedad de prueba
INSERT INTO property_types (
    property_type_id,
    name
) VALUES (
    UNHEX('33333333333333333333333333333333'),
    'APARTMENT'
);

-- Insertar edificio en Villa María, Córdoba
INSERT INTO buildings (
    deleted,
    latitude,
    longitude,
    building_id,
    building_type_building_type_id,
    user_user_id,
    address,
    description,
    name
) VALUES (
    0,
    -32.4094,
    -63.2432,
    UNHEX('123e4567e89b12d3a456426614174000'),
    UNHEX('22222222222222222222222222222222'),
    UNHEX('11111111111111111111111111111111'),
    'Bv. España 1000, Villa María, Córdoba',
    'Edificio de prueba en Villa María, Córdoba, para desarrollo frontend',
    'Torre Villa María'
);

-- Insertar propiedades asociadas al building y al tipo de propiedad
INSERT INTO properties (
    latitude,
    longitude,
    building_building_id,
    property_id,
    property_type_property_type_id,
    address
) VALUES
    (
        -32.4095,
        -63.2431,
        UNHEX('123e4567e89b12d3a456426614174000'),
        UNHEX('44444444444444444444444444444444'),
        UNHEX('33333333333333333333333333333333'),
        'Bv. España 1000, Villa María, Córdoba, Piso 1, Depto A'
    ),
    (
        -32.4093,
        -63.2433,
        UNHEX('123e4567e89b12d3a456426614174000'),
        UNHEX('55555555555555555555555555555555'),
        UNHEX('33333333333333333333333333333333'),
        'Bv. España 1000, Villa María, Córdoba, Piso 2, Depto B'
    );