-- Users seed
INSERT IGNORE INTO users (
    user_id, dni, password, birth_date, first_name, last_name, email, address, phone_number, cuit, role, activated, deleted
) VALUES
    (UNHEX('AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA'), '44244003', '$2a$10$XuN33pdjkfpv3SfA8I.jm.hQHV3aempTZquspVNsBSCUkxmKzydjS', '1990-01-01', 'PROP', 'INQ', 'admin@propinq.com', 'Sin dirección', '+5493534123456', '20123456789', 'ADMIN', 1, 0),
    (UNHEX('11111111111111111111111111111111'), '12345678', '$2a$10$XuN33pdjkfpv3SfA8I.jm.hQHV3aempTZquspVNsBSCUkxmKzydjS', '1985-05-15', 'Juan', 'Propietario', 'propietario@propinq.com', 'Villa María, Córdoba', '+5493534987654', '20123456780', 'OWNER', 1, 0),
    -- Tenants for seed data
    (UNHEX(REPLACE('22222222-2222-2222-2222-222222222222', '-', '')), '30111222', '$2a$10$XuN33pdjkfpv3SfA8I.jm.hQHV3aempTZquspVNsBSCUkxmKzydjS', '1995-03-20', 'Lucía',   'Gómez',    'lucia.gomez@ejemplo.com',    'Córdoba Capital',       '+5493514111222', '27301112221', 'TENANT', 1, 0),
    (UNHEX(REPLACE('33333333-3333-3333-3333-333333333333', '-', '')), '31222333', '$2a$10$XuN33pdjkfpv3SfA8I.jm.hQHV3aempTZquspVNsBSCUkxmKzydjS', '1998-07-10', 'Pedro',   'Martínez', 'pedro.martinez@ejemplo.com', 'Rosario, Santa Fe',     '+5493414222333', '20312223331', 'TENANT', 1, 0),
    (UNHEX(REPLACE('44444444-4444-4444-4444-444444444444', '-', '')), '32333444', '$2a$10$XuN33pdjkfpv3SfA8I.jm.hQHV3aempTZquspVNsBSCUkxmKzydjS', '1992-11-05', 'Camila',  'López',    'camila.lopez@ejemplo.com',   'Villa María, Córdoba',  '+5493534333444', '27323334441', 'TENANT', 1, 0),
    (UNHEX(REPLACE('55555555-5555-5555-5555-555555555555', '-', '')), '33444555', '$2a$10$XuN33pdjkfpv3SfA8I.jm.hQHV3aempTZquspVNsBSCUkxmKzydjS', '2000-01-22', 'Tomás',   'Fernández','tomas.fernandez@ejemplo.com','Bell Ville, Córdoba',   '+5493534444555', '20334445551', 'TENANT', 1, 0),
    (UNHEX(REPLACE('66666666-6666-6666-6666-666666666666', '-', '')), '34555666', '$2a$10$XuN33pdjkfpv3SfA8I.jm.hQHV3aempTZquspVNsBSCUkxmKzydjS', '1997-09-14', 'Valentina','Ruiz',    'valentina.ruiz@ejemplo.com', 'San Francisco, Córdoba','+5493564555666', '27345556661', 'TENANT', 1, 0);
