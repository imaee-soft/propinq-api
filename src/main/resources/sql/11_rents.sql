-- Rents seed: linked to ACCEPTED contacts, varied states/indices/dates
-- contract column gets a small placeholder blob (empty PDF header)

INSERT IGNORE INTO rents (
    rent_id, rent_date, rent_due_date, rent_state, payday, rent_price,
    raise_index, raise_months, contract,
    cancellation_reason, cancellation_date,
    contact_contact_id
) VALUES
-- ═══ R01: Prop 0001 — Lucía (ACTIVE, ICL, vence jun 2026 — dentro de 90 días) ═══
(UNHEX(REPLACE('A0000001-0001-4000-A000-000000000001', '-', '')),
 '2025-11-01', '2026-06-15', 'ACTIVE', 10, 180000,
 0, 6, X'255044462D',
 NULL, NULL,
 UNHEX(REPLACE('C0000001-0001-4000-A000-000000000001', '-', ''))),

-- ═══ R02: Prop 0002 — Pedro (ACTIVE, IPC, 36 meses, vence nov 2028) ═══
(UNHEX(REPLACE('A0000002-0002-4000-A000-000000000001', '-', '')),
 '2025-12-01', '2028-11-01', 'ACTIVE', 5, 220000,
 1, 3, X'255044462D',
 NULL, NULL,
 UNHEX(REPLACE('C0000002-0002-4000-A000-000000000001', '-', ''))),

-- ═══ R03: Prop 0003 — Camila (ACTIVE, CasaPropia, vence jul 2026 — dentro de 90 días) ═══
(UNHEX(REPLACE('A0000003-0003-4000-A000-000000000001', '-', '')),
 '2026-01-01', '2026-07-01', 'ACTIVE', 1, 350000,
 2, 4, X'255044462D',
 NULL, NULL,
 UNHEX(REPLACE('C0000003-0003-4000-A000-000000000001', '-', ''))),

-- ═══ R04: Prop 0005 — Tomás (CANCELLED, ICL, estaba por 24 meses) ═══
(UNHEX(REPLACE('A0000004-0004-4000-A000-000000000001', '-', '')),
 '2025-10-01', '2027-10-01', 'CANCELLED', 10, 95000,
 0, 6, X'255044462D',
 'El inquilino se mudó por trabajo a otra provincia.', '2026-03-15',
 UNHEX(REPLACE('C0000005-0005-4000-A000-000000000001', '-', ''))),

-- ═══ R05: Prop 0006 — Lucía (ACTIVE, CAC, 36 meses, vence sep 2028) ═══
(UNHEX(REPLACE('A0000005-0005-4000-A000-000000000001', '-', '')),
 '2025-09-01', '2028-09-01', 'ACTIVE', 15, 280000,
 3, 6, X'255044462D',
 NULL, NULL,
 UNHEX(REPLACE('C0000006-0006-4000-A000-000000000001', '-', ''))),

-- ═══ R06: Prop 0007 — Valentina (ACTIVE, UVA, 24 meses, vence mar 2028) ═══
(UNHEX(REPLACE('A0000006-0006-4000-A000-000000000001', '-', '')),
 '2026-03-01', '2028-03-01', 'ACTIVE', 1, 120000,
 7, 3, X'255044462D',
 NULL, NULL,
 UNHEX(REPLACE('C0000007-0007-4000-A000-000000000001', '-', ''))),

-- ═══ R07: Prop 0003 — Lucía (DONE, CER, contrato finalizado) ═══
(UNHEX(REPLACE('A0000007-0007-4000-A000-000000000001', '-', '')),
 '2025-01-01', '2025-12-01', 'DONE', 5, 150000,
 4, 6, X'255044462D',
 NULL, NULL,
 UNHEX(REPLACE('C0000003-0003-4000-A000-000000000003', '-', '')));
