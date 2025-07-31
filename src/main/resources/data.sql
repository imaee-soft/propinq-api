-- Insertar usuario de prueba
INSERT IGNORE INTO users (
    user_id,
    dni,
    password,
    birth_date,
    first_name,
    last_name,
    email,
    address,
    phone_number,
    cuit,
    role,
    activated,
    deleted
) VALUES (
             UNHEX('11111111111111111111111111111111'), -- user_id (UUID en hex)
             '12345678',                             -- dni
             'demo_password',                        -- password
             '1990-05-15',                           -- birth_date (formato YYYY-MM-DD)
             'Juan',                                 -- first_name
             'Pérez',                                -- last_name
             'juan.perez@ejemplo.com',               -- email
             'Sin dirección',                        -- address
             '+5493534123456',                       -- phone_number
             NULL,                                   -- cuit (puede ir NULL o un string)
             'OWNER',                                -- role (string, Enum)
             1,                                      -- activated (true)
             0                                       -- deleted (false)
         );

-- Insertar tipo de propiedad de prueba
INSERT IGNORE INTO property_types (property_type_id,
                            name,
                            description,
                            deleted)
VALUES (UNHEX('33333333333333333333333333333333'),
        'APARTMENT',
        'Apartamento de dos habitaciones.',
        0);

-- Insertar edificio en Villa María, Córdoba
INSERT IGNORE INTO buildings (building_id,
                       name,
                       description,
                       address,
                       latitude,
                       longitude,
                       user_user_id,
                       building_type,
                       deleted)
VALUES (UNHEX('123e4567e89b12d3a456426614174000'),
        'Torre Villa María',
        'Edificio de prueba en Villa María, Córdoba, para desarrollo frontend',
        'Bv. España 1000, Villa María, Córdoba',
        -32.4094,
        -63.2432,
        UNHEX('11111111111111111111111111111111'), -- user_id
        'EDIFICIO',
        0);

INSERT IGNORE INTO images(url,deleted)
VALUES('https://climalit.es/blog/wp-content/uploads/2018/05/edificios-eficientes-1280x1280.jpg',0),
       ('https://img.freepik.com/foto-gratis/tiro-vertical-edificio-blanco-cielo-despejado_181624-4575.jpg?semt=ais_hybrid&w=740',0);
-- Insertar propiedades asociadas al building y al tipo de propiedad
INSERT IGNORE INTO buildings_images (buildings_building_id, images_url)
VALUES(UNHEX('123e4567e89b12d3a456426614174000'),'https://climalit.es/blog/wp-content/uploads/2018/05/edificios-eficientes-1280x1280.jpg');

INSERT IGNORE INTO buildings_images (buildings_building_id, images_url)
VALUES (UNHEX('123e4567e89b12d3a456426614174000'),'https://img.freepik.com/foto-gratis/tiro-vertical-edificio-blanco-cielo-despejado_181624-4575.jpg?semt=ais_hybrid&w=740');

INSERT IGNORE INTO properties (property_id,
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