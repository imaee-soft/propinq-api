DROP VIEW IF EXISTS `owner_contact_facts`;

CREATE VIEW `owner_contact_facts` AS
SELECT
    BIN_TO_UUID(`o`.`user_id`)           AS `owner_id`,
    `o`.`first_name`                     AS `owner_first_name`,
    `o`.`last_name`                      AS `owner_last_name`,
    `o`.`email`                          AS `owner_email`,

    BIN_TO_UUID(`p`.`property_id`)       AS `property_id`,
    `p`.`title`                          AS `property_title`,
    `p`.`address`                        AS `property_address`,
    `p`.`price`                          AS `property_price`,
    CASE `p`.`property_type`
        WHEN 0 THEN 'APARTAMENTO'
        WHEN 1 THEN 'CASA'
    END                                  AS `property_type`,
    `p`.`bedrooms`                       AS `property_bedrooms`,
    `p`.`bathrooms`                      AS `property_bathrooms`,
    `p`.`created_at`                     AS `property_created_at`,

    BIN_TO_UUID(`b`.`building_id`)       AS `building_id`,
    `b`.`name`                           AS `building_name`,

    BIN_TO_UUID(`c`.`contact_id`)        AS `contact_id`,
    CASE `c`.`state`
        WHEN 'ACCEPTED' THEN 'Aceptado'
        WHEN 'CREATED'  THEN 'Creado'
        WHEN 'REJECTED' THEN 'Rechazado'
        WHEN 'RENTED'   THEN 'Alquilado'
        WHEN 'UNSETTLED' THEN 'Sin resolver'
    END COLLATE utf8mb4_0900_ai_ci        AS `contact_state`,
    `c`.`issue_date`                     AS `contact_issue_date`,
    `c`.`answer_date`                    AS `contact_answer_date`,
    `c`.`contact_message`                AS `contact_message`,
    `c`.`contact_answer`                 AS `contact_answer`,
    `c`.`cancellation_reason`            AS `contact_cancellation_reason`,
    `c`.`cancellation_date`              AS `contact_cancellation_date`,
    CASE
        WHEN `c`.`answer_date` IS NOT NULL
        THEN TIMESTAMPDIFF(MINUTE, `c`.`issue_date`, `c`.`answer_date`)
    END                                  AS `contact_response_minutes`,

    BIN_TO_UUID(`c`.`issuer_user_id`)    AS `contact_issuer_id`,
    `i`.`first_name`                     AS `issuer_first_name`,
    `i`.`last_name`                      AS `issuer_last_name`,
    `i`.`email`                          AS `issuer_email`,

    BIN_TO_UUID(`r`.`rent_id`)           AS `rent_id`,
    CASE `r`.`rent_state`
        WHEN 'ACTIVE'    THEN 'Activo'
        WHEN 'CANCELLED' THEN 'Cancelado'
        WHEN 'DONE'      THEN 'Finalizado'
    END COLLATE utf8mb4_0900_ai_ci        AS `rent_state`,
    `r`.`rent_date`                      AS `rent_start_date`,
    `r`.`rent_due_date`                  AS `rent_due_date`,
    `r`.`rent_price`                     AS `rent_price`,
    `r`.`payday`                         AS `rent_payday`,
    CASE `r`.`raise_index`
        WHEN 0 THEN 'ICL'
        WHEN 1 THEN 'IPC'
        WHEN 2 THEN 'CasaPropia'
        WHEN 3 THEN 'CAC'
        WHEN 4 THEN 'CER'
        WHEN 5 THEN 'IS'
        WHEN 6 THEN 'IPIM'
        WHEN 7 THEN 'UVA'
    END                                  AS `rent_raise_index`,
    `r`.`raise_months`                   AS `rent_raise_months`,
    TIMESTAMPDIFF(MONTH, `r`.`rent_date`, `r`.`rent_due_date`)
                                         AS `rent_duration_months`,
    `r`.`cancellation_reason`            AS `rent_cancellation_reason`,
    `r`.`cancellation_date`              AS `rent_cancellation_date`

FROM `properties`   `p`
JOIN `users`         `o` ON `p`.`user_user_id`          = `o`.`user_id`
LEFT JOIN `buildings`  `b` ON `p`.`building_building_id`  = `b`.`building_id`
JOIN `contacts`      `c` ON `c`.`property_property_id`  = `p`.`property_id`
LEFT JOIN `users`      `i` ON `c`.`issuer_user_id`        = `i`.`user_id`
LEFT JOIN `rents`      `r` ON `r`.`contact_contact_id`    = `c`.`contact_id`
WHERE `p`.`deleted` = 0;
