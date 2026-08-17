-- Datos demo actuales (fuente: propinq-infra/scripts/seed-demo-properties.sql).
-- Alineado con CT102 / www.propinq.online.

-- Property to images relations
INSERT IGNORE INTO properties_images (properties_property_id, images_url) VALUES
(UNHEX(REPLACE('a1000000-0000-4000-8000-000000000001', '-', '')), 'https://images.unsplash.com/photo-1564013799919-ab600027ffc6?w=1200&q=80'),
(UNHEX(REPLACE('a1000000-0000-4000-8000-000000000001', '-', '')), 'https://images.unsplash.com/photo-1600566753190-17f0baa2a6c3?w=1200&q=80'),
(UNHEX(REPLACE('a1000000-0000-4000-8000-000000000002', '-', '')), 'https://images.unsplash.com/photo-1600596542815-ffad4c1539a9?w=1200&q=80'),
(UNHEX(REPLACE('a1000000-0000-4000-8000-000000000003', '-', '')), 'https://images.unsplash.com/photo-1600585154340-be6161a56a0c?w=1200&q=80'),
(UNHEX(REPLACE('a1000000-0000-4000-8000-000000000004', '-', '')), 'https://images.unsplash.com/photo-1600047509807-ba8f99d2cd0c?w=1200&q=80'),
(UNHEX(REPLACE('a1000000-0000-4000-8000-000000000004', '-', '')), 'https://images.unsplash.com/photo-1600210492486-724fe5c67fb0?w=1200&q=80'),
(UNHEX(REPLACE('a1000000-0000-4000-8000-000000000005', '-', '')), 'https://images.unsplash.com/photo-1600607687939-ce8a6c25118c?w=1200&q=80'),
(UNHEX(REPLACE('a1000000-0000-4000-8000-000000000006', '-', '')), 'https://images.unsplash.com/photo-1570129477492-45c003edd2be?w=1200&q=80'),
(UNHEX(REPLACE('a1000000-0000-4000-8000-000000000007', '-', '')), 'https://images.unsplash.com/photo-1512917774080-9991f1c4c750?w=1200&q=80'),
(UNHEX(REPLACE('a1000000-0000-4000-8000-000000000008', '-', '')), 'https://images.unsplash.com/photo-1580587771525-78b9eaa60c84?w=1200&q=80'),
(UNHEX(REPLACE('a1000000-0000-4000-8000-000000000009', '-', '')), 'https://images.unsplash.com/photo-1568605114967-8130f3a36994?w=1200&q=80');


INSERT IGNORE INTO properties_images (properties_property_id, images_url) VALUES
(UNHEX(REPLACE('a2000000-0000-4000-8000-000000000001', '-', '')), 'https://images.unsplash.com/photo-1502672260266-1c1ef2d93688?w=1200&q=80'),
(UNHEX(REPLACE('a2000000-0000-4000-8000-000000000002', '-', '')), 'https://images.unsplash.com/photo-1522708323590-d24dbb6b0267?w=1200&q=80'),
(UNHEX(REPLACE('a2000000-0000-4000-8000-000000000003', '-', '')), 'https://images.unsplash.com/photo-1560448204-e02f11c3d0e2?w=1200&q=80'),
(UNHEX(REPLACE('a2000000-0000-4000-8000-000000000004', '-', '')), 'https://images.unsplash.com/photo-1505693416388-ac5ce068fe85?w=1200&q=80'),
(UNHEX(REPLACE('a2000000-0000-4000-8000-000000000005', '-', '')), 'https://images.unsplash.com/photo-1536376072261-38c75010e6c9?w=1200&q=80'),
(UNHEX(REPLACE('a2000000-0000-4000-8000-000000000006', '-', '')), 'https://images.unsplash.com/photo-1554995207-c18c203602cb?w=1200&q=80'),
(UNHEX(REPLACE('a2000000-0000-4000-8000-000000000007', '-', '')), 'https://images.unsplash.com/photo-1560185127-6ed189bf02f4?w=1200&q=80'),
(UNHEX(REPLACE('a2000000-0000-4000-8000-000000000008', '-', '')), 'https://images.unsplash.com/photo-1630699144867-37acec97df5e?w=1200&q=80'),
(UNHEX(REPLACE('a2000000-0000-4000-8000-000000000009', '-', '')), 'https://images.unsplash.com/photo-1616594039964-ae9021a400a0?w=1200&q=80');
