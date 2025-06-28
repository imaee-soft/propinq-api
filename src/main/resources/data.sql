-- INSERT PRELOAD PROVINCES
INSERT INTO province (name) VALUES('Buenos Aires');
INSERT INTO province (name) VALUES('Ciudad Autónoma de Buenos Aires');
INSERT INTO province (name) VALUES('Catamarca');
INSERT INTO province (name) VALUES('Chaco');
INSERT INTO province (name) VALUES('Chubut');
INSERT INTO province (name) VALUES('Córdoba');
INSERT INTO province (name) VALUES('Corrientes');
INSERT INTO province (name) VALUES('Entre Ríos');
INSERT INTO province (name) VALUES('Formosa');
INSERT INTO province (name) VALUES('Jujuy');
INSERT INTO province (name) VALUES('La Pampa');
INSERT INTO province (name) VALUES('La Rioja');
INSERT INTO province (name) VALUES('Mendoza');
INSERT INTO province (name) VALUES('Misiones');
INSERT INTO province (name) VALUES('Neuquén');
INSERT INTO province (name) VALUES('Río Negro');
INSERT INTO province (name) VALUES('Salta');
INSERT INTO province (name) VALUES('San Juan');
INSERT INTO province (name) VALUES('San Luis');
INSERT INTO province (name) VALUES('Santa Cruz');
INSERT INTO province (name) VALUES('Santa Fe');
INSERT INTO province (name) VALUES('Santiago del Estero');
INSERT INTO province (name) VALUES('Tierra del Fuego, Antártida e Islas del Atlántico Sur');
INSERT INTO province (name) VALUES('Tucumán');

-- Insertar usuario de prueba
INSERT INTO users (
    user_id,
    username,
    password,
    first_name,
    last_name,
    email,
    phone_number,
    role
) VALUES (
             UNHEX('11111111111111111111111111111111'),
             'demo_owner',
             'demo_password',
             'Juan',
             'Pérez',
             'juan.perez@ejemplo.com',
             '+5493534123456',
             'REAL_ESTATE_OWNER'
         );

-- Insertar tipo de edificio de prueba
INSERT INTO building_types (
    building_type_id,
    name
) VALUES (
             UNHEX('22222222222222222222222222222222'),
             'RESIDENTIAL'
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
    building_id,
    name,
    description,
    address,
    latitude,
    longitude,
    user_user_id,
    building_type_building_type_id
) VALUES (
             UNHEX('123e4567e89b12d3a456426614174000'),
             'Torre Villa María',
             'Edificio de prueba en Villa María, Córdoba, para desarrollo frontend',
             'Bv. España 1000, Villa María, Córdoba',
             -32.4094,
             -63.2432,
             UNHEX('11111111111111111111111111111111'),
             UNHEX('22222222222222222222222222222222')
         );

-- Insertar propiedades asociadas al building y al tipo de propiedad
INSERT INTO properties (
    property_id,
    address,
    latitude,
    longitude,
    building_building_id,
    property_type_property_type_id
) VALUES (
             UNHEX('44444444444444444444444444444444'),
             'Bv. España 1000, Villa María, Córdoba, Piso 1, Depto A',
             -32.4095,
             -63.2431,
             UNHEX('123e4567e89b12d3a456426614174000'),
             UNHEX('33333333333333333333333333333333')
         ), (
             UNHEX('55555555555555555555555555555555'),
             'Bv. España 1000, Villa María, Córdoba, Piso 2, Depto B',
             -32.4093,
             -63.2433,
             UNHEX('123e4567e89b12d3a456426614174000'),
             UNHEX('33333333333333333333333333333333')
         );