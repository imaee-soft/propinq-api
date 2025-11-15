-- Building to images relations
INSERT IGNORE INTO buildings_images (buildings_building_id, images_url) VALUES
  (UNHEX('123e4567e89b12d3a456426614174000'), 'https://climalit.es/blog/wp-content/uploads/2018/05/edificios-eficientes-1280x1280.jpg'),
  (UNHEX('123e4567e89b12d3a456426614174000'), 'https://img.freepik.com/foto-gratis/tiro-vertical-edificio-blanco-cielo-despejado_181624-4575.jpg?semt=ais_hybrid&w=740');