-- Datos demo actuales (fuente: propinq-infra/scripts/seed-demo-properties.sql).
-- Alineado con CT102 / www.propinq.online.

-- Images seed
INSERT IGNORE INTO images (url, deleted, public_id, file_name) VALUES
('https://images.unsplash.com/photo-1564013799919-ab600027ffc6?w=1200&q=80', 0, 'house-ext-01', 'house-ext-01.jpg'),
('https://images.unsplash.com/photo-1600596542815-ffad4c1539a9?w=1200&q=80', 0, 'house-ext-02', 'house-ext-02.jpg'),
('https://images.unsplash.com/photo-1600585154340-be6161a56a0c?w=1200&q=80', 0, 'house-ext-03', 'house-ext-03.jpg'),
('https://images.unsplash.com/photo-1600047509807-ba8f99d2cd0c?w=1200&q=80', 0, 'house-ext-04', 'house-ext-04.jpg'),
('https://images.unsplash.com/photo-1600607687939-ce8a6c25118c?w=1200&q=80', 0, 'house-ext-05', 'house-ext-05.jpg'),
('https://images.unsplash.com/photo-1570129477492-45c003edd2be?w=1200&q=80', 0, 'house-ext-06', 'house-ext-06.jpg'),
('https://images.unsplash.com/photo-1512917774080-9991f1c4c750?w=1200&q=80', 0, 'house-ext-07', 'house-ext-07.jpg'),
('https://images.unsplash.com/photo-1580587771525-78b9eaa60c84?w=1200&q=80', 0, 'house-ext-08', 'house-ext-08.jpg'),
('https://images.unsplash.com/photo-1568605114967-8130f3a36994?w=1200&q=80', 0, 'house-ext-09', 'house-ext-09.jpg'),
('https://images.unsplash.com/photo-1605276374104-dee2c83bf63f?w=1200&q=80', 0, 'house-ext-10', 'house-ext-10.jpg'),
('https://images.unsplash.com/photo-1600566753190-17f0baa2a6c3?w=1200&q=80', 0, 'house-int-01', 'house-int-01.jpg'),
('https://images.unsplash.com/photo-1600210492486-724fe5c67fb0?w=1200&q=80', 0, 'house-int-02', 'house-int-02.jpg'),
-- Edificios exteriores
('https://images.unsplash.com/photo-1486406146926-c627a92ad1ab?w=1200&q=80', 0, 'bldg-ext-01', 'bldg-ext-01.jpg'),
('https://images.unsplash.com/photo-1545324418-cc1a3fa10c00?w=1200&q=80', 0, 'bldg-ext-02', 'bldg-ext-02.jpg'),
('https://images.unsplash.com/photo-1460317441624-23cfa11eb17a?w=1200&q=80', 0, 'bldg-ext-03', 'bldg-ext-03.jpg'),
('https://images.unsplash.com/photo-1486325212027-8081e4854094?w=1200&q=80', 0, 'bldg-ext-04', 'bldg-ext-04.jpg'),
('https://images.unsplash.com/photo-1493809842364-78817add7ffb?w=1200&q=80', 0, 'bldg-ext-05', 'bldg-ext-05.jpg'),
('https://images.unsplash.com/photo-1582407947304-fd86f028f716?w=1200&q=80', 0, 'bldg-ext-06', 'bldg-ext-06.jpg'),
-- Departamentos interiores
('https://images.unsplash.com/photo-1502672260266-1c1ef2d93688?w=1200&q=80', 0, 'apt-int-01', 'apt-int-01.jpg'),
('https://images.unsplash.com/photo-1522708323590-d24dbb6b0267?w=1200&q=80', 0, 'apt-int-02', 'apt-int-02.jpg'),
('https://images.unsplash.com/photo-1493663284031-b7e3aefcae8e?w=1200&q=80', 0, 'apt-int-03', 'apt-int-03.jpg'),
('https://images.unsplash.com/photo-1560448204-e02f11c3d0e2?w=1200&q=80', 0, 'apt-int-04', 'apt-int-04.jpg'),
('https://images.unsplash.com/photo-1505693416388-ac5ce068fe85?w=1200&q=80', 0, 'apt-int-05', 'apt-int-05.jpg'),
('https://images.unsplash.com/photo-1536376072261-38c75010e6c9?w=1200&q=80', 0, 'apt-int-06', 'apt-int-06.jpg'),
('https://images.unsplash.com/photo-1554995207-c18c203602cb?w=1200&q=80', 0, 'apt-int-07', 'apt-int-07.jpg'),
('https://images.unsplash.com/photo-1560185127-6ed189bf02f4?w=1200&q=80', 0, 'apt-int-08', 'apt-int-08.jpg'),
('https://images.unsplash.com/photo-1630699144867-37acec97df5e?w=1200&q=80', 0, 'apt-int-09', 'apt-int-09.jpg'),
('https://images.unsplash.com/photo-1616594039964-ae9021a400a0?w=1200&q=80', 0, 'apt-int-10', 'apt-int-10.jpg'),
('https://images.unsplash.com/photo-1615874959474-d609969a20ed?w=1200&q=80', 0, 'apt-int-11', 'apt-int-11.jpg'),
('https://images.unsplash.com/photo-1616486338812-3dadae4b4ace?w=1200&q=80', 0, 'apt-int-12', 'apt-int-12.jpg'),
('https://images.unsplash.com/photo-1618221195710-dd6b41faaea6?w=1200&q=80', 0, 'apt-int-13', 'apt-int-13.jpg'),
('https://images.unsplash.com/photo-1615529182904-14819c35db37?w=1200&q=80', 0, 'apt-int-14', 'apt-int-14.jpg'),
('https://images.unsplash.com/photo-1616137466211-f939a420be84?w=1200&q=80', 0, 'apt-int-15', 'apt-int-15.jpg'),
('https://images.unsplash.com/photo-1600585154526-990dced4db0d?w=1200&q=80', 0, 'apt-int-16', 'apt-int-16.jpg'),
('https://images.unsplash.com/photo-1600573472592-401b489a3cdc?w=1200&q=80', 0, 'apt-int-17', 'apt-int-17.jpg'),
('https://images.unsplash.com/photo-1600047509358-9dc75507daeb?w=1200&q=80', 0, 'apt-int-18', 'apt-int-18.jpg');

