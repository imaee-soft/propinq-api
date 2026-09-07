-- Users seed (demo). Contraseña de todos: admin123
-- bcrypt = $2a$10$udlZWB5DwOzA1I1QJZ3usOVV764/LGq4Ir4/oYZPjMzCbiccTaT6u
INSERT INTO users (
    user_id, password, birth_date, first_name, last_name, email,
    address, phone_number, role, activated, deleted
) VALUES
    (
        UNHEX('AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA'),
        '$2a$10$udlZWB5DwOzA1I1QJZ3usOVV764/LGq4Ir4/oYZPjMzCbiccTaT6u',
        '1990-01-01',
        'Admin',
        'Propinq',
        'admin@propinq.com',
        'Sin dirección',
        '+5493534000001',
        'ADMIN',
        1,
        0
    ),
    (
        UNHEX('11111111111111111111111111111111'),
        '$2a$10$udlZWB5DwOzA1I1QJZ3usOVV764/LGq4Ir4/oYZPjMzCbiccTaT6u',
        '1985-05-15',
        'Juan',
        'Propietario',
        'propietario@propinq.com',
        'Villa María, Córdoba',
        '+5493534987654',
        'OWNER',
        1,
        0
    ),
    (
        UNHEX('22222222222222222222222222222222'),
        '$2a$10$udlZWB5DwOzA1I1QJZ3usOVV764/LGq4Ir4/oYZPjMzCbiccTaT6u',
        '1992-08-20',
        'Ana',
        'Inquilina',
        'inquilino@propinq.com',
        'Córdoba Capital',
        '+5493514000003',
        'TENANT',
        1,
        0
    )
ON DUPLICATE KEY UPDATE
    password = VALUES(password),
    role = VALUES(role),
    activated = 1,
    deleted = 0,
    first_name = VALUES(first_name),
    last_name = VALUES(last_name);
