-- Users seed
-- users: 
-- Owner:
-- propietario@propinq.com
-- admin123

-- Admin:
-- admin@propinq.com
-- admin123

-- Tenant:
-- inquilino@gmail.com
-- admin123
INSERT IGNORE INTO users (
    user_id,
    activated,
    address,
    birth_date,
    deleted,
    email,
    first_name,
    last_name,
    password,
    phone_number,
    role
) VALUES
(
    UNHEX('AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA'),
    1,
    'Sin dirección',
    '1990-01-01',
    0,
    'admin@propinq.com',
    'PROP',
    'INQ',
    '$2a$10$XuN33pdjkfpv3SfA8I.jm.hQHV3aempTZquspVNsBSCUkxmKzydjS',
    '+5493534123456',
    'ADMIN'
),
(
    UNHEX('11111111111111111111111111111111'),
    1,
    'Villa María, Córdoba',
    '1985-05-15',
    0,
    'propietario@propinq.com',
    'Juan',
    'Propietario',
    '$2a$10$XuN33pdjkfpv3SfA8I.jm.hQHV3aempTZquspVNsBSCUkxmKzydjS',
    '+5493534987654',
    'OWNER'
),
(
    UNHEX('22222222222222222222222222222222'),
    1,
    'Buenos Aires, Argentina',
    '1995-08-20',
    0,
    'inquilino@propinq.com',
    'Pedro',
    'Inquilino',
    '$2a$10$XuN33pdjkfpv3SfA8I.jm.hQHV3aempTZquspVNsBSCUkxmKzydjS',
    '+5491123456789',
    'TENANT'
);