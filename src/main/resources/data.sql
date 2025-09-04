-- Actualizar usuario existente o insertar si no existe
DELETE FROM users WHERE email = 'admin@propinq.com';
INSERT INTO users (
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
    UNHEX('AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA'),
    --admin123 (fuerza de 10)
    '$2a$10$XuN33pdjkfpv3SfA8I.jm.hQHV3aempTZquspVNsBSCUkxmKzydjS',
    'PROP',
    'INQ',
    'admin@propinq.com',
    '+5493534123456',
    'ADMIN',
    0,
    1,
    'Sin dirección'
);

-- Insertar tipo de edificio de prueba
INSERT IGNORE INTO building_types (building_type_id,
                            name,
                            deleted)
VALUES (UNHEX('22222222222222222222222222222222'),
        'RESIDENTIAL',
        0);


-- Insertar tipo de propiedad de prueba
INSERT IGNORE INTO property_types (
    property_type_id,
    name,
    description,
    deleted
) VALUES (
             UNHEX('33333333333333333333333333333333'),
             'APARTMENT',
             'Apartamento de dos habitaciones.',
             0
         );

-- Insertar edificio en Villa María, Córdoba
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

-- Insertar imágenes necesarias en la tabla images
INSERT IGNORE INTO images (url, deleted, public_id) VALUES
                                                        ('https://climalit.es/blog/wp-content/uploads/2018/05/edificios-eficientes-1280x1280.jpg', 0, 'edificios-eficientes-1280x1280'),
                                                        ('https://img.freepik.com/foto-gratis/tiro-vertical-edificio-blanco-cielo-despejado_181624-4575.jpg?semt=ais_hybrid&w=740', 0, 'edificio-blanco-cielo-despejado'),
                                                        ('https://images.adsttc.com/media/images/590c/93c8/e58e/ce1f/9800/004b/slideshow/17-03-02_425.jpg?1493996481', 0, '17-03-02_425'),
                                                        ('https://images.adsttc.com/media/images/590c/945c/e58e/cee9/b200/0014/slideshow/17-03-02_359.jpg?1493996629', 0, '17-03-02_359'),
                                                        ('https://images.adsttc.com/media/images/590c/940d/e58e/ce1f/9800/004e/slideshow/17-03-02_397.jpg?1493996550', 0, '17-03-02_397'),
                                                        ('https://images.adsttc.com/media/images/590c/932a/e58e/ce1f/9800/0041/slideshow/17-03-02_447.jpg?1493996326', 0, '17-03-02_447'),
                                                        ('https://images.adsttc.com/media/images/590c/93f6/e58e/ce1f/9800/004d/slideshow/17-03-02_406.jpg?1493996527', 0, '17-03-02_406'),
                                                        ('https://images.adsttc.com/media/images/590d/2d63/e58e/ce34/8300/002f/slideshow/2016_10_METAFORMA_POZNAN-7055.jpg?1494035802', 0, 'POZNAN-7055'),
                                                        ('https://images.adsttc.com/media/images/590d/2e3d/e58e/ce34/8300/0034/slideshow/2016_10_METAFORMA_POZNAN-7105.jpg?1494036012', 0, 'POZNAN-7105'),
                                                        ('https://images.adsttc.com/media/images/590d/2da4/e58e/ce65/4200/01ce/slideshow/2016_10_METAFORMA_POZNAN-7066.jpg?1494035867', 0, 'POZNAN-7066'),
                                                        ('https://images.homify.com/c_fill,f_auto,h_700,q_auto/v1453219822/p/photo/image/1255680/Arquiteta_Camila_Castilho-84.jpg', 0, 'camila-castilho-84'),
                                                        ('https://images.homify.com/c_fill,f_auto,h_700,q_auto/v1453219892/p/photo/image/1255695/10.jpg', 0, 'homify-10'),
                                                        ('https://images.homify.com/c_fill,f_auto,h_700,q_auto/v1453219874/p/photo/image/1255691/05.jpg', 0, 'homify-05');

-- Insertar propiedades asociadas al building y al tipo de propiedad
INSERT IGNORE INTO buildings_images (buildings_building_id, images_url) VALUES
                                                                            (UNHEX('123e4567e89b12d3a456426614174000'), 'https://climalit.es/blog/wp-content/uploads/2018/05/edificios-eficientes-1280x1280.jpg'),
                                                                            (UNHEX('123e4567e89b12d3a456426614174000'), 'https://img.freepik.com/foto-gratis/tiro-vertical-edificio-blanco-cielo-despejado_181624-4575.jpg?semt=ais_hybrid&w=740');

-- Propiedad 1: en el building
INSERT IGNORE INTO properties (
    property_id,
    address,
    latitude,
    longitude,
    building_building_id,
    property_type_property_type_id,
    price,
    description,
    title,
    floor,
    bedrooms,
    bathrooms,
    pets_allowed,
    area,
    apartment_number,
    user_user_id,
    deleted
) VALUES (
             UNHEX('AAAABBBBCCCCDDDDEEEEFFFF00000001'),
             'Bv. España 1000, Villa María, Córdoba, Piso 3, Depto C',
             -32.4092,
             -63.2434,
             UNHEX('123e4567e89b12d3a456426614174000'),
             UNHEX('33333333333333333333333333333333'),
             90000,
             'Departamento moderno con vista al parque.',
             'Depto 3 ambientes Premium',
             3,
             2,
             2,
             1,
             85.0,
             'C',
             UNHEX('11111111111111111111111111111111'),
             0
         );

-- Imágenes propiedad 1
INSERT IGNORE INTO properties_images (properties_property_id, images_url) VALUES
                                                                              (UNHEX('AAAABBBBCCCCDDDDEEEEFFFF00000001'), 'https://images.adsttc.com/media/images/590c/93c8/e58e/ce1f/9800/004b/slideshow/17-03-02_425.jpg?1493996481'),
                                                                              (UNHEX('AAAABBBBCCCCDDDDEEEEFFFF00000001'), 'https://images.adsttc.com/media/images/590c/945c/e58e/cee9/b200/0014/slideshow/17-03-02_359.jpg?1493996629');

-- Propiedad 2: en el building
INSERT IGNORE INTO properties (
    property_id,
    address,
    latitude,
    longitude,
    building_building_id,
    property_type_property_type_id,
    price,
    description,
    title,
    floor,
    bedrooms,
    bathrooms,
    pets_allowed,
    area,
    apartment_number,
    user_user_id,
    deleted
) VALUES (
             UNHEX('AAAABBBBCCCCDDDDEEEEFFFF00000002'),
             'Bv. España 1000, Villa María, Córdoba, Piso 4, Depto D',
             -32.4091,
             -63.2435,
             UNHEX('123e4567e89b12d3a456426614174000'),
             UNHEX('33333333333333333333333333333333'),
             110000,
             'Departamento familiar con balcón aterrazado.',
             'Depto 4 ambientes con balcón',
             4,
             3,
             2,
             0,
             120.0,
             'D',
             UNHEX('11111111111111111111111111111111'),
             0
         );

-- Imágenes propiedad 2
INSERT IGNORE INTO properties_images (properties_property_id, images_url) VALUES
                                                                              (UNHEX('AAAABBBBCCCCDDDDEEEEFFFF00000002'), 'https://images.adsttc.com/media/images/590c/940d/e58e/ce1f/9800/004e/slideshow/17-03-02_397.jpg?1493996550'),
                                                                              (UNHEX('AAAABBBBCCCCDDDDEEEEFFFF00000002'), 'https://images.adsttc.com/media/images/590c/932a/e58e/ce1f/9800/0041/slideshow/17-03-02_447.jpg?1493996326'),
                                                                              (UNHEX('AAAABBBBCCCCDDDDEEEEFFFF00000002'), 'https://images.adsttc.com/media/images/590c/93f6/e58e/ce1f/9800/004d/slideshow/17-03-02_406.jpg?1493996527');

-- Propiedad 3: en el building
INSERT IGNORE INTO properties (
    property_id,
    address,
    latitude,
    longitude,
    building_building_id,
    property_type_property_type_id,
    price,
    description,
    title,
    floor,
    bedrooms,
    bathrooms,
    pets_allowed,
    area,
    apartment_number,
    user_user_id,
    deleted
) VALUES (
             UNHEX('AAAABBBBCCCCDDDDEEEEFFFF00000003'),
             'Bv. España 1000, Villa María, Córdoba, Piso 5, Depto E',
             -32.4090,
             -63.2436,
             UNHEX('123e4567e89b12d3a456426614174000'),
             UNHEX('33333333333333333333333333333333'),
             140000,
             'Departamento de lujo con terraza propia.',
             'Penthouse con terraza',
             5,
             4,
             3,
             1,
             180.0,
             'E',
             UNHEX('11111111111111111111111111111111'),
             0
         );

-- Imágenes propiedad 3
INSERT IGNORE INTO properties_images (properties_property_id, images_url) VALUES
                                                                              (UNHEX('AAAABBBBCCCCDDDDEEEEFFFF00000003'), 'https://images.adsttc.com/media/images/590d/2d63/e58e/ce34/8300/002f/slideshow/2016_10_METAFORMA_POZNAN-7055.jpg?1494035802'),
                                                                              (UNHEX('AAAABBBBCCCCDDDDEEEEFFFF00000003'), 'https://images.adsttc.com/media/images/590d/2e3d/e58e/ce34/8300/0034/slideshow/2016_10_METAFORMA_POZNAN-7105.jpg?1494036012'),
                                                                              (UNHEX('AAAABBBBCCCCDDDDEEEEFFFF00000003'), 'https://images.adsttc.com/media/images/590d/2da4/e58e/ce65/4200/01ce/slideshow/2016_10_METAFORMA_POZNAN-7066.jpg?1494035867');

-- Propiedad 4: SIN building
INSERT IGNORE INTO properties (
    property_id,
    address,
    latitude,
    longitude,
    building_building_id,
    property_type_property_type_id,
    price,
    description,
    title,
    floor,
    bedrooms,
    bathrooms,
    pets_allowed,
    area,
    apartment_number,
    user_user_id,
    deleted
) VALUES (
             UNHEX('AAAABBBBCCCCDDDDEEEEFFFF00000004'),
             'Bv. España 1500, Villa María, Córdoba, Piso 1, Depto A',
             -32.4097,
             -63.2428,
             NULL,
             UNHEX('33333333333333333333333333333333'),
             130000,
             'Departamento independiente en zona céntrica.',
             'Depto independiente 2 ambientes',
             1,
             2,
             1,
             1,
             65.0,
             'A',
             UNHEX('11111111111111111111111111111111'),
             0
         );

-- Imágenes propiedad 4 (SIN building)
INSERT IGNORE INTO properties_images (properties_property_id, images_url) VALUES
                                                                              (UNHEX('AAAABBBBCCCCDDDDEEEEFFFF00000004'), 'https://images.homify.com/c_fill,f_auto,h_700,q_auto/v1453219822/p/photo/image/1255680/Arquiteta_Camila_Castilho-84.jpg'),
                                                                              (UNHEX('AAAABBBBCCCCDDDDEEEEFFFF00000004'), 'https://images.homify.com/c_fill,f_auto,h_700,q_auto/v1453219892/p/photo/image/1255695/10.jpg'),
                                                                              (UNHEX('AAAABBBBCCCCDDDDEEEEFFFF00000004'), 'https://images.homify.com/c_fill,f_auto,h_700,q_auto/v1453219874/p/photo/image/1255691/05.jpg');