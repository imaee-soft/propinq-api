-- Contacts seed: varied states, dates spread across months, multiple properties
-- Contacts that led to a rent use state ACCEPTED; the rent record tracks the rental.
--
-- Owner 1: Juan Propietario (11111111-...) — owns properties AAAABBBB-...-0001 to 0010
-- Tenants: 22222222 (Lucía), 33333333 (Pedro), 44444444 (Camila), 55555555 (Tomás), 66666666 (Valentina)

-- Fix Hibernate-generated CHECK constraint that incorrectly blocks REJECTED/RENTED/UNSETTLED
-- MySQL ENUMs are 1-based, but Hibernate generates 0-based ordinal constraints
SET @constraint_exists = (
  SELECT COUNT(*) FROM INFORMATION_SCHEMA.CHECK_CONSTRAINTS
  WHERE CONSTRAINT_SCHEMA = DATABASE() AND CONSTRAINT_NAME = 'contacts_chk_1'
);
SET @drop_sql = IF(@constraint_exists > 0, 'ALTER TABLE contacts DROP CHECK contacts_chk_1', 'SELECT 1');
PREPARE stmt FROM @drop_sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

INSERT IGNORE INTO contacts (
    contact_id, contact_message, contact_answer, issue_date, answer_date, state,
    cancellation_reason, cancellation_date,
    issuer_user_id, property_property_id
) VALUES
-- ═══ Prop 0001 (Depto 3 amb Premium) — 4 contactos ═══
-- C01: ACCEPTED → tiene rent (Lucía)
(UNHEX(REPLACE('C0000001-0001-4000-A000-000000000001', '-', '')),
 'Hola, me interesa el depto de 3 ambientes. ¿Podemos coordinar una visita?',
 '¡Sí, por supuesto! Te espero el sábado a las 10.',
 '2025-10-05 09:30:00', '2025-10-05 11:15:00', 'ACCEPTED',
 NULL, NULL,
 UNHEX(REPLACE('22222222-2222-2222-2222-222222222222', '-', '')),
 UNHEX(REPLACE('AAAABBBB-CCCC-DDDD-EEEE-FFFF00000001', '-', ''))),

-- C02: REJECTED (Pedro)
(UNHEX(REPLACE('C0000001-0001-4000-A000-000000000002', '-', '')),
 'Buen día, ¿está disponible para alquilar? Soy estudiante.',
 'Disculpá, no acepto estudiantes sin garante.',
 '2025-10-12 14:00:00', '2025-10-13 10:00:00', 'REJECTED',
 NULL, NULL,
 UNHEX(REPLACE('33333333-3333-3333-3333-333333333333', '-', '')),
 UNHEX(REPLACE('AAAABBBB-CCCC-DDDD-EEEE-FFFF00000001', '-', ''))),

-- C03: CREATED sin responder (Valentina)
(UNHEX(REPLACE('C0000001-0001-4000-A000-000000000003', '-', '')),
 'Hola, ¿sigue disponible? Me mudaría en marzo.',
 NULL,
 '2026-03-20 18:45:00', NULL, 'CREATED',
 NULL, NULL,
 UNHEX(REPLACE('66666666-6666-6666-6666-666666666666', '-', '')),
 UNHEX(REPLACE('AAAABBBB-CCCC-DDDD-EEEE-FFFF00000001', '-', ''))),

-- C04: ACCEPTED (Tomás)
(UNHEX(REPLACE('C0000001-0001-4000-A000-000000000004', '-', '')),
 'Buenas tardes, me interesa el depto. ¿Tiene cochera?',
 'Sí, incluye una cochera. Coordinemos para que lo veas.',
 '2026-01-08 10:00:00', '2026-01-08 18:30:00', 'ACCEPTED',
 NULL, NULL,
 UNHEX(REPLACE('55555555-5555-5555-5555-555555555555', '-', '')),
 UNHEX(REPLACE('AAAABBBB-CCCC-DDDD-EEEE-FFFF00000001', '-', ''))),

-- ═══ Prop 0002 (Depto 4 amb con balcón) — 3 contactos ═══
-- C05: ACCEPTED → tiene rent (Pedro)
(UNHEX(REPLACE('C0000002-0002-4000-A000-000000000001', '-', '')),
 'Hola, me interesa el de 4 ambientes. Somos una familia de 4.',
 'Perfecto, es ideal para familias. Agendamos visita.',
 '2025-11-01 08:00:00', '2025-11-01 09:20:00', 'ACCEPTED',
 NULL, NULL,
 UNHEX(REPLACE('33333333-3333-3333-3333-333333333333', '-', '')),
 UNHEX(REPLACE('AAAABBBB-CCCC-DDDD-EEEE-FFFF00000002', '-', ''))),

-- C06: REJECTED (Camila)
(UNHEX(REPLACE('C0000002-0002-4000-A000-000000000002', '-', '')),
 'Buenas, ¿aceptan mascotas? Tengo un golden.',
 'Lamentablemente no aceptamos mascotas grandes.',
 '2025-11-15 16:30:00', '2025-11-16 08:00:00', 'REJECTED',
 NULL, NULL,
 UNHEX(REPLACE('44444444-4444-4444-4444-444444444444', '-', '')),
 UNHEX(REPLACE('AAAABBBB-CCCC-DDDD-EEEE-FFFF00000002', '-', ''))),

-- C07: CREATED (Valentina)
(UNHEX(REPLACE('C0000002-0002-4000-A000-000000000003', '-', '')),
 '¡Hola! Vi el depto publicado, ¿cuándo puedo visitarlo?',
 NULL,
 '2026-04-01 12:00:00', NULL, 'CREATED',
 NULL, NULL,
 UNHEX(REPLACE('66666666-6666-6666-6666-666666666666', '-', '')),
 UNHEX(REPLACE('AAAABBBB-CCCC-DDDD-EEEE-FFFF00000002', '-', ''))),

-- ═══ Prop 0003 (Penthouse con terraza) — 3 contactos ═══
-- C08: ACCEPTED → tiene rent (Camila)
(UNHEX(REPLACE('C0000003-0003-4000-A000-000000000001', '-', '')),
 'Me encanta el penthouse, ¿podemos avanzar rápido?',
 'Dale, preparemos la documentación.',
 '2025-12-01 10:00:00', '2025-12-01 10:45:00', 'ACCEPTED',
 NULL, NULL,
 UNHEX(REPLACE('44444444-4444-4444-4444-444444444444', '-', '')),
 UNHEX(REPLACE('AAAABBBB-CCCC-DDDD-EEEE-FFFF00000003', '-', ''))),

-- C09: REJECTED (Tomás)
(UNHEX(REPLACE('C0000003-0003-4000-A000-000000000002', '-', '')),
 'Hola, ¿el precio es negociable?',
 'No, el precio es fijo. Disculpá.',
 '2025-12-10 09:00:00', '2025-12-12 14:00:00', 'REJECTED',
 NULL, NULL,
 UNHEX(REPLACE('55555555-5555-5555-5555-555555555555', '-', '')),
 UNHEX(REPLACE('AAAABBBB-CCCC-DDDD-EEEE-FFFF00000003', '-', ''))),

-- C10: ACCEPTED (Lucía) — contacto en negociación
(UNHEX(REPLACE('C0000003-0003-4000-A000-000000000003', '-', '')),
 'Hola, estoy muy interesada. ¿Acepta garantía de seguro de caución?',
 'Sí, aceptamos. Coordinemos.',
 '2026-02-15 11:00:00', '2026-02-15 17:30:00', 'ACCEPTED',
 NULL, NULL,
 UNHEX(REPLACE('22222222-2222-2222-2222-222222222222', '-', '')),
 UNHEX(REPLACE('AAAABBBB-CCCC-DDDD-EEEE-FFFF00000003', '-', ''))),

-- ═══ Prop 0004 (Depto independiente 2 amb) — 2 contactos ═══
-- C11: ACCEPTED (Valentina)
(UNHEX(REPLACE('C0000004-0004-4000-A000-000000000001', '-', '')),
 'Hola, busco algo chico para vivir sola. ¿Está disponible?',
 '¡Sí! Es perfecto para una persona. Te espero.',
 '2026-01-20 15:00:00', '2026-01-20 16:10:00', 'ACCEPTED',
 NULL, NULL,
 UNHEX(REPLACE('66666666-6666-6666-6666-666666666666', '-', '')),
 UNHEX(REPLACE('AAAABBBB-CCCC-DDDD-EEEE-FFFF00000004', '-', ''))),

-- C12: CREATED sin responder (Tomás)
(UNHEX(REPLACE('C0000004-0004-4000-A000-000000000002', '-', '')),
 'Buenas, ¿incluye expensas?',
 NULL,
 '2026-03-25 08:30:00', NULL, 'CREATED',
 NULL, NULL,
 UNHEX(REPLACE('55555555-5555-5555-5555-555555555555', '-', '')),
 UNHEX(REPLACE('AAAABBBB-CCCC-DDDD-EEEE-FFFF00000004', '-', ''))),

-- ═══ Prop 0005 (Casa 2 amb económica) — 3 contactos ═══
-- C13: ACCEPTED → tiene rent (Tomás)
(UNHEX(REPLACE('C0000005-0005-4000-A000-000000000001', '-', '')),
 'Hola, me interesa la casa económica. ¿Puedo ir a verla?',
 'Claro, vení mañana a las 10.',
 '2025-09-15 11:00:00', '2025-09-15 14:00:00', 'ACCEPTED',
 NULL, NULL,
 UNHEX(REPLACE('55555555-5555-5555-5555-555555555555', '-', '')),
 UNHEX(REPLACE('AAAABBBB-CCCC-DDDD-EEEE-FFFF00000005', '-', ''))),

-- C14: REJECTED (Lucía)
(UNHEX(REPLACE('C0000005-0005-4000-A000-000000000002', '-', '')),
 '¿El barrio es seguro? Me preocupa un poco la zona.',
 'Es una zona tranquila, pero entiendo tu preocupación. Ya está alquilada.',
 '2025-09-20 17:00:00', '2025-09-22 09:00:00', 'REJECTED',
 NULL, NULL,
 UNHEX(REPLACE('22222222-2222-2222-2222-222222222222', '-', '')),
 UNHEX(REPLACE('AAAABBBB-CCCC-DDDD-EEEE-FFFF00000005', '-', ''))),

-- C15: CREATED sin responder (Pedro)
(UNHEX(REPLACE('C0000005-0005-4000-A000-000000000003', '-', '')),
 'Hola, quería saber si todavía está disponible.',
 NULL,
 '2026-04-10 10:00:00', NULL, 'CREATED',
 NULL, NULL,
 UNHEX(REPLACE('33333333-3333-3333-3333-333333333333', '-', '')),
 UNHEX(REPLACE('AAAABBBB-CCCC-DDDD-EEEE-FFFF00000005', '-', ''))),

-- ═══ Prop 0006 (Casa 5 amb) — 2 contactos ═══
-- C16: ACCEPTED → tiene rent (Lucía)
(UNHEX(REPLACE('C0000006-0006-4000-A000-000000000001', '-', '')),
 'Buenas, me interesa para mi familia. Somos 5.',
 'Es ideal para vos. Coordinemos la documentación.',
 '2025-08-20 09:00:00', '2025-08-20 10:30:00', 'ACCEPTED',
 NULL, NULL,
 UNHEX(REPLACE('22222222-2222-2222-2222-222222222222', '-', '')),
 UNHEX(REPLACE('AAAABBBB-CCCC-DDDD-EEEE-FFFF00000006', '-', ''))),

-- C17: REJECTED (Camila)
(UNHEX(REPLACE('C0000006-0006-4000-A000-000000000002', '-', '')),
 'Hola, ¿se puede alquilar por temporada (6 meses)?',
 'No, solo contratos de mínimo 2 años.',
 '2025-11-05 13:00:00', '2025-11-07 16:00:00', 'REJECTED',
 NULL, NULL,
 UNHEX(REPLACE('44444444-4444-4444-4444-444444444444', '-', '')),
 UNHEX(REPLACE('AAAABBBB-CCCC-DDDD-EEEE-FFFF00000006', '-', ''))),

-- ═══ Prop 0007 (Casa 2 amb) — 2 contactos ═══
-- C18: ACCEPTED → tiene rent (Valentina)
(UNHEX(REPLACE('C0000007-0007-4000-A000-000000000001', '-', '')),
 'Hola, me gusta la casa. ¿Cuándo puedo mudarme?',
 'A partir del 1ro del mes que viene.',
 '2026-02-01 08:00:00', '2026-02-01 11:00:00', 'ACCEPTED',
 NULL, NULL,
 UNHEX(REPLACE('66666666-6666-6666-6666-666666666666', '-', '')),
 UNHEX(REPLACE('AAAABBBB-CCCC-DDDD-EEEE-FFFF00000007', '-', ''))),

-- C19: CREATED sin responder (Camila)
(UNHEX(REPLACE('C0000007-0007-4000-A000-000000000002', '-', '')),
 'Buenas tardes, ¿el alquiler incluye servicios?',
 NULL,
 '2026-03-28 19:00:00', NULL, 'CREATED',
 NULL, NULL,
 UNHEX(REPLACE('44444444-4444-4444-4444-444444444444', '-', '')),
 UNHEX(REPLACE('AAAABBBB-CCCC-DDDD-EEEE-FFFF00000007', '-', ''))),

-- ═══ Prop 0008 (Casa 3 amb) — 2 contactos ═══
-- C20: ACCEPTED (Pedro)
(UNHEX(REPLACE('C0000008-0008-4000-A000-000000000001', '-', '')),
 'Hola, ¿tiene patio? Necesito espacio para mi hijo.',
 'Sí, tiene un patio grande. Vení a verla.',
 '2026-03-01 10:00:00', '2026-03-01 12:30:00', 'ACCEPTED',
 NULL, NULL,
 UNHEX(REPLACE('33333333-3333-3333-3333-333333333333', '-', '')),
 UNHEX(REPLACE('AAAABBBB-CCCC-DDDD-EEEE-FFFF00000008', '-', ''))),

-- C21: REJECTED (Lucía)
(UNHEX(REPLACE('C0000008-0008-4000-A000-000000000002', '-', '')),
 '¿Se puede pintar las paredes?',
 'No, se entrega pintada y así debe devolverse.',
 '2026-03-10 14:00:00', '2026-03-11 09:00:00', 'REJECTED',
 NULL, NULL,
 UNHEX(REPLACE('22222222-2222-2222-2222-222222222222', '-', '')),
 UNHEX(REPLACE('AAAABBBB-CCCC-DDDD-EEEE-FFFF00000008', '-', ''))),

-- ═══ Prop 0009 (Casa 3 amb) — 1 contacto ═══
-- C22: CREATED (Camila) — sin responder hace mucho
(UNHEX(REPLACE('C0000009-0009-4000-A000-000000000001', '-', '')),
 'Hola, ¿cuánto salen las expensas aproximadamente?',
 NULL,
 '2026-02-20 16:00:00', NULL, 'CREATED',
 NULL, NULL,
 UNHEX(REPLACE('44444444-4444-4444-4444-444444444444', '-', '')),
 UNHEX(REPLACE('AAAABBBB-CCCC-DDDD-EEEE-FFFF00000009', '-', ''))),

-- ═══ Prop 0010 (Casa 2 amb independiente) — 1 contacto ═══
-- C23: ACCEPTED (Tomás)
(UNHEX(REPLACE('C0000010-0010-4000-A000-000000000001', '-', '')),
 'Me interesa mucho, ¿podemos firmar esta semana?',
 'Dale, el jueves te paso el contrato.',
 '2026-04-01 09:00:00', '2026-04-01 09:15:00', 'ACCEPTED',
 NULL, NULL,
 UNHEX(REPLACE('55555555-5555-5555-5555-555555555555', '-', '')),
 UNHEX(REPLACE('AAAABBBB-CCCC-DDDD-EEEE-FFFF00000010', '-', '')));
