-- Users seed
INSERT IGNORE INTO users (
    user_id, dni, password, birth_date, first_name, last_name, email, address, phone_number, cuit, role, activated, deleted
) VALUES
    (UNHEX('AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA'), '44244003', '$2a$10$XuN33pdjkfpv3SfA8I.jm.hQHV3aempTZquspVNsBSCUkxmKzydjS', '1990-01-01', 'PROP', 'INQ', 'admin@propinq.com', 'Sin dirección', '+5493534123456', '20123456789', 'ADMIN', 1, 0),
    (UNHEX('11111111111111111111111111111111'), '12345678', '$2a$10$XuN33pdjkfpv3SfA8I.jm.hQHV3aempTZquspVNsBSCUkxmKzydjS', '1985-05-15', 'Juan', 'Propietario', 'propietario@propinq.com', 'Villa María, Córdoba', '+5493534987654', '20123456780', 'OWNER', 1, 0);
