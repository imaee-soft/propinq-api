-- Limpiar tablas
DELETE FROM buildings_images;
DELETE FROM properties_images;
DELETE FROM properties;
DELETE FROM buildings;
DELETE FROM images;
DELETE FROM building_types;
DELETE FROM property_types;
DELETE FROM users;

-- Usuarios
INSERT INTO users (
    user_id, dni, password, birth_date, first_name, last_name, email, address, phone_number, cuit, role, activated, deleted
) VALUES
    (UNHEX('AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA'), '12345678', '$2a$10$XuN33pdjkfpv3SfA8I.jm.hQHV3aempTZquspVNsBSCUkxmKzydjS', '1990-01-01', 'PROP', 'INQ', 'admin@propinq.com', 'Sin dirección', '+5493534123456', '20-12345678-9', 'ADMIN', 1, 0),
    (UNHEX('BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB'), '23456789', '$2a$10$XuN33pdjkfpv3SfA8I.jm.hQHV3aempTZquspVNsBSCUkxmKzydjS', '1985-05-12', 'Ana', 'García', 'ana@propinq.com', 'Av. San Martín 1200', '+5493512345678', '27-23456789-5', 'OWNER', 1, 0),
    (UNHEX('CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC'), '34567890', '$2a$10$XuN33pdjkfpv3SfA8I.jm.hQHV3aempTZquspVNsBSCUkxmKzydjS', '1995-07-23', 'Carlos', 'Pérez', 'carlos@propinq.com', 'Ruta 9 km 550', '+5493519876543', '20-34567890-6', 'TENANT', 1, 0)
;

-- Tipos de edificios
INSERT INTO building_types (building_type_id, name, deleted) VALUES
    (UNHEX('22222222222222222222222222222222'), 'EDIFICIO', 0),
    (UNHEX('44444444444444444444444444444444'), 'COMPLEJO', 0)
;

-- Tipos de propiedades
INSERT INTO property_types (property_type_id, name, description, deleted) VALUES
    (UNHEX('33333333333333333333333333333333'), 'APARTMENT', 'Apartamento de dos habitaciones.', 0),
    (UNHEX('55555555555555555555555555555555'), 'HOUSE', 'Casa unifamiliar de una o más plantas.', 0)
;

-- Edificios
INSERT INTO buildings (
    building_id, name, description, address, latitude, longitude, user_user_id, building_type, deleted
) VALUES
    (UNHEX('123e4567e89b12d3a456426614174000'), 'Torre Villa María', 'Edificio de prueba en Villa María, Córdoba, para desarrollo frontend', 'Bv. España 1000, Villa María, Córdoba', -32.4094, -63.2432, UNHEX('AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA'), 'EDIFICIO', 0),
    (UNHEX('99999999999999999999999999999999'), 'Galería Comercial Centro', 'Edificio comercial con locales en planta baja y oficinas en pisos superiores', 'San Martín 200, Villa María, Córdoba', -32.4101, -63.2438, UNHEX('BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB'), 'COMPLEJO', 0)
;

-- Imágenes
INSERT INTO images (url, deleted, public_id) VALUES
    ('https://picsum.photos/800/600?random=1', 0, 'random-1'),
    ('https://picsum.photos/800/600?random=2', 0, 'random-2'),
    ('https://picsum.photos/800/600?random=3', 0, 'random-3'),
    ('https://picsum.photos/800/600?random=4', 0, 'random-4'),
    ('https://picsum.photos/800/600?random=5', 0, 'random-5')
;

-- Relaciones edificios - imágenes
INSERT INTO buildings_images (buildings_building_id, images_url) VALUES
    (UNHEX('123e4567e89b12d3a456426614174000'), 'https://picsum.photos/800/600?random=1'),
    (UNHEX('99999999999999999999999999999999'), 'https://picsum.photos/800/600?random=2')
;

-- Propiedades
INSERT INTO properties (
    property_id, address, latitude, longitude, building_building_id, property_type_property_type_id,
    price, description, title, floor, bedrooms, bathrooms, pets_allowed, area, apartment_number, user_user_id, deleted
) VALUES
    (UNHEX('AAAABBBBCCCCDDDDEEEEFFFF00000005'), 'Bv. España 1000, Villa María, Córdoba, Piso 6, Depto F', -32.4089, -63.2437,
        UNHEX('123e4567e89b12d3a456426614174000'), UNHEX('33333333333333333333333333333333'),
        95000, 'Departamento compacto ideal para estudiantes.', 'Depto 2 ambientes económico', 6, 1, 1, 1, 45.0, 'F', UNHEX('AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA'), 0),
    (UNHEX('AAAABBBBCCCCDDDDEEEEFFFF00000006'), 'Bv. España 1000, Villa María, Córdoba, Piso 7, Depto G', -32.4088, -63.2438,
        UNHEX('123e4567e89b12d3a456426614174000'), UNHEX('33333333333333333333333333333333'),
        125000, 'Departamento moderno con cocina integrada.', 'Depto 3 ambientes luminoso', 7, 2, 2, 0, 75.0, 'G', UNHEX('AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA'), 0),
    (UNHEX('AAAABBBBCCCCDDDDEEEEFFFF00000007'), 'Bv. España 1000, Villa María, Córdoba, Piso 8, Depto H', -32.4087, -63.2439,
        UNHEX('123e4567e89b12d3a456426614174000'), UNHEX('33333333333333333333333333333333'),
        145000, 'Departamento premium con doble balcón.', 'Depto 4 ambientes Premium', 8, 3, 2, 1, 130.0, 'H', UNHEX('AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA'), 0),
    (UNHEX('AAAABBBBCCCCDDDDEEEEFFFF00000008'), 'Bv. España 1000, Villa María, Córdoba, Piso 9, Depto I', -32.4086, -63.2440,
        UNHEX('123e4567e89b12d3a456426614174000'), UNHEX('33333333333333333333333333333333'),
        180000, 'Penthouse con vista panorámica.', 'Penthouse de lujo', 9, 4, 3, 0, 200.0, 'I', UNHEX('AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA'), 0),
    (UNHEX('AAAABBBBCCCCDDDDEEEEFFFF00000009'), 'Av. San Martín 500, Villa María, Córdoba, Piso 1, Depto A', -32.4101, -63.2420,
        NULL, UNHEX('33333333333333333333333333333333'),
        80000, 'Departamento céntrico ideal para oficina o vivienda.', 'Monoambiente céntrico', 1, 1, 1, 0, 40.0, 'A', UNHEX('AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA'), 0),
    (UNHEX('AAAABBBBCCCCDDDDEEEEFFFF0000000A'), 'Av. San Martín 500, Villa María, Córdoba, Piso 2, Depto B', -32.4102, -63.2421,
        NULL, UNHEX('33333333333333333333333333333333'),
        95000, 'Departamento renovado, excelente ubicación.', 'Depto 2 ambientes refaccionado', 2, 2, 1, 1, 55.0, 'B', UNHEX('AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA'), 0),
    (UNHEX('AAAABBBBCCCCDDDDEEEEFFFF0000000B'), 'Av. Alem 800, Villa María, Córdoba, Casa 1', -32.4110, -63.2445,
        NULL, UNHEX('33333333333333333333333333333333'),
        160000, 'Casa independiente con patio y cochera.', 'Casa céntrica con patio', 0, 3, 2, 1, 140.0, '1', UNHEX('AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA'), 0),
    (UNHEX('AAAABBBBCCCCDDDDEEEEFFFF0000000C'), 'Av. Alem 810, Villa María, Córdoba, Casa 2', -32.4111, -63.2446,
        NULL, UNHEX('33333333333333333333333333333333'),
        175000, 'Casa amplia ideal para familia.', 'Casa 4 ambientes con cochera', 0, 4, 3, 0, 180.0, '2', UNHEX('AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA'), 0),
    (UNHEX('AAAABBBBCCCCDDDDEEEEFFFF0000000D'), 'Ruta 9 km 555, Villa María, Córdoba, Dúplex 1', -32.4200, -63.2500,
        NULL, UNHEX('33333333333333333333333333333333'),
        200000, 'Dúplex moderno en zona residencial.', 'Dúplex 3 ambientes Premium', 0, 3, 2, 1, 160.0, '1', UNHEX('AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA'), 0),
    (UNHEX('AAAABBBBCCCCDDDDEEEEFFFF0000000E'), 'Ruta 9 km 555, Villa María, Córdoba, Dúplex 2', -32.4201, -63.2501,
        NULL, UNHEX('33333333333333333333333333333333'),
        210000, 'Dúplex con terraza privada y cochera.', 'Dúplex 4 ambientes con terraza', 0, 4, 3, 0, 190.0, '2', UNHEX('AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA'), 0),
    (UNHEX('AAAABBBBCCCCDDDDEEEEFFFF0000000F'), 'Bv. España 1200, Villa María, Córdoba, Piso 2, Depto B', -32.4085, -63.2441,
        NULL, UNHEX('33333333333333333333333333333333'),
        100000, 'Departamento en edificio nuevo, con amenities.', 'Depto 2 ambientes moderno', 2, 2, 1, 1, 70.0, 'B', UNHEX('AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA'), 0)
;


-- Imágenes propiedades
INSERT INTO properties_images (properties_property_id, images_url) VALUES
    (UNHEX('AAAABBBBCCCCDDDDEEEEFFFF00000005'), 'https://picsum.photos/800/600?random=3'),
    (UNHEX('AAAABBBBCCCCDDDDEEEEFFFF00000006'), 'https://picsum.photos/800/600?random=4'),
    (UNHEX('AAAABBBBCCCCDDDDEEEEFFFF00000007'), 'https://picsum.photos/800/600?random=5')
;
