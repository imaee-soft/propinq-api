-- Property to images relations
INSERT IGNORE INTO properties_images (properties_property_id, images_url) VALUES
  (UNHEX(REPLACE('AAAABBBB-CCCC-DDDD-EEEE-FFFF00000001', '-', '')), 'https://images.adsttc.com/media/images/590c/93c8/e58e/ce1f/9800/004b/slideshow/17-03-02_425.jpg?1493996481'),
  (UNHEX(REPLACE('AAAABBBB-CCCC-DDDD-EEEE-FFFF00000001', '-', '')), 'https://images.adsttc.com/media/images/590c/945c/e58e/cee9/b200/0014/slideshow/17-03-02_359.jpg?1493996629'),
  (UNHEX(REPLACE('AAAABBBB-CCCC-DDDD-EEEE-FFFF00000002', '-', '')), 'https://images.adsttc.com/media/images/590c/940d/e58e/ce1f/9800/004e/slideshow/17-03-02_397.jpg?1493996550'),
  (UNHEX(REPLACE('AAAABBBB-CCCC-DDDD-EEEE-FFFF00000002', '-', '')), 'https://images.adsttc.com/media/images/590c/932a/e58e/ce1f/9800/0041/slideshow/17-03-02_447.jpg?1493996326'),
  (UNHEX(REPLACE('AAAABBBB-CCCC-DDDD-EEEE-FFFF00000002', '-', '')), 'https://images.adsttc.com/media/images/590c/93f6/e58e/ce1f/9800/004d/slideshow/17-03-02_406.jpg?1493996527'),
  (UNHEX(REPLACE('AAAABBBB-CCCC-DDDD-EEEE-FFFF00000003', '-', '')), 'https://images.adsttc.com/media/images/590d/2d63/e58e/ce34/8300/002f/slideshow/2016_10_METAFORMA_POZNAN-7055.jpg?1494035802'),
  (UNHEX(REPLACE('AAAABBBB-CCCC-DDDD-EEEE-FFFF00000003', '-', '')), 'https://images.adsttc.com/media/images/590d/2e3d/e58e/ce34/8300/0034/slideshow/2016_10_METAFORMA_POZNAN-7105.jpg?1494036012'),
  (UNHEX(REPLACE('AAAABBBB-CCCC-DDDD-EEEE-FFFF00000003', '-', '')), 'https://images.adsttc.com/media/images/590d/2da4/e58e/ce65/4200/01ce/slideshow/2016_10_METAFORMA_POZNAN-7066.jpg?1494035867'),
  (UNHEX(REPLACE('AAAABBBB-CCCC-DDDD-EEEE-FFFF00000004', '-', '')), 'https://images.homify.com/c_fill,f_auto,h_700,q_auto/v1453219822/p/photo/image/1255680/Arquiteta_Camila_Castilho-84.jpg'),
  (UNHEX(REPLACE('AAAABBBB-CCCC-DDDD-EEEE-FFFF00000004', '-', '')), 'https://images.homify.com/c_fill,f_auto,h_700,q_auto/v1453219892/p/photo/image/1255695/10.jpg'),
  (UNHEX(REPLACE('AAAABBBB-CCCC-DDDD-EEEE-FFFF00000004', '-', '')), 'https://images.homify.com/c_fill,f_auto,h_700,q_auto/v1453219874/p/photo/image/1255691/05.jpg');

  