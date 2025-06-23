-- Insertar usuario de prueba
INSERT INTO users (user_id,
                   username,
                   password,
                   first_name,
                   last_name,
                   email,
                   phone_number,
                   role,
                   deleted)
VALUES (UNHEX('11111111111111111111111111111111'),
        'demo_owner',
        'demo_password',
        'Juan',
        'Pérez',
        'juan.perez@ejemplo.com',
        '+5493534123456',
        'OWNER',
        0);

-- Insertar tipo de edificio de prueba
INSERT INTO building_types (building_type_id,
                            name,
                            deleted)
VALUES (UNHEX('22222222222222222222222222222222'),
        'RESIDENTIAL',
        0);

-- Insertar tipo de propiedad de prueba
INSERT INTO property_types (property_type_id,
                            name,
                            description,
                            deleted)
VALUES (UNHEX('33333333333333333333333333333333'),
        'APARTMENT',
        'Apartamento de dos habitaciones.',
        0);

-- Insertar edificio en Villa María, Córdoba
INSERT INTO buildings (building_id,
                       name,
                       description,
                       address,
                       latitude,
                       longitude,
                       user_id,
                       building_type_building_type_id,
                       deleted)
VALUES (UNHEX('123e4567e89b12d3a456426614174000'),
        'Torre Villa María',
        'Edificio de prueba en Villa María, Córdoba, para desarrollo frontend',
        'Bv. España 1000, Villa María, Córdoba',
        -32.4094,
        -63.2432,
        UNHEX('11111111111111111111111111111111'), -- user_id
        UNHEX('22222222222222222222222222222222'), -- building_type_id
        0);

-- Insertar propiedades asociadas al building y al tipo de propiedad
INSERT INTO properties (property_id,
                        address,
                        latitude,
                        longitude,
                        building_building_id,
                        property_type_property_type_id,
                        deleted)
VALUES (UNHEX('44444444444444444444444444444444'),
        'Bv. España 1000, Villa María, Córdoba, Piso 1, Depto A',
        -32.4095,
        -63.2431,
        UNHEX('123e4567e89b12d3a456426614174000'),
        UNHEX('33333333333333333333333333333333'),
        0),
       (UNHEX('55555555555555555555555555555555'),
        'Bv. España 1000, Villa María, Córdoba, Piso 2, Depto B',
        -32.4093,
        -63.2433,
        UNHEX('123e4567e89b12d3a456426614174000'),
        UNHEX('33333333333333333333333333333333'),
        0);