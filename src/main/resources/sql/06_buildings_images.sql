-- Datos demo actuales (fuente: propinq-infra/scripts/seed-demo-properties.sql).
-- Alineado con CT102 / www.propinq.online.

-- Building to images relations
INSERT IGNORE INTO buildings_images (buildings_building_id, images_url) VALUES
(UNHEX(REPLACE('b1000000-0000-4000-8000-000000000001', '-', '')), 'https://images.unsplash.com/photo-1486406146926-c627a92ad1ab?w=1200&q=80'),
(UNHEX(REPLACE('b1000000-0000-4000-8000-000000000001', '-', '')), 'https://images.unsplash.com/photo-1545324418-cc1a3fa10c00?w=1200&q=80'),
(UNHEX(REPLACE('b1000000-0000-4000-8000-000000000002', '-', '')), 'https://images.unsplash.com/photo-1460317441624-23cfa11eb17a?w=1200&q=80'),
(UNHEX(REPLACE('b1000000-0000-4000-8000-000000000002', '-', '')), 'https://images.unsplash.com/photo-1486325212027-8081e4854094?w=1200&q=80'),
(UNHEX(REPLACE('b1000000-0000-4000-8000-000000000003', '-', '')), 'https://images.unsplash.com/photo-1493809842364-78817add7ffb?w=1200&q=80'),
(UNHEX(REPLACE('b1000000-0000-4000-8000-000000000003', '-', '')), 'https://images.unsplash.com/photo-1582407947304-fd86f028f716?w=1200&q=80');

