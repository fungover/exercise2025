-- Insert Scoutkårer
INSERT INTO patrol (name, district, contact_person, contact_email) VALUES
                                                                       ('Sjungande dalen Scoutkår', 'Skellefteå', 'Eva Andersson', 'eva@ska.scout.se'),
                                                                       ('Täby Scoutkår', 'Stockholm', 'Lars Berg', 'lars@taby.scout.se'),
                                                                       ('Nacka Scoutkår', 'Stockholm', 'Maria Nilsson', 'maria@nacka.scout.se'),
                                                                       ('Sjulnäs EFS Scout', 'Piteå', 'Johan Persson', 'johan@sollentuna.scout.se');

-- Insert Allergener
INSERT INTO allergen (name) VALUES
                                                       ('Laktos'),
                                                       ('Gluten'),
                                                       ('Nötter'),
                                                       ('Jordnötter'),
                                                       ('Ägg'),
                                                       ('Fisk'),
                                                       ('Skaldjur'),
                                                       ('Soja'),
                                                       ('Sesam'),
                                                       ('Selleri');

-- Insert Event
INSERT INTO event (name, start_date, end_date, location, max_participants) VALUES
                                                                               ('Sommarläger Mjösjöliden 2026', '2026-07-10', '2026-07-17', 'Mjösjöliden', 2000),
                                                                               ('Blåsweekend', '2026-02-13', '2026-02-15', 'Storstrand, Piteå', 80),
                                                                               ('Vinterläger Åre 2027', '2027-02-14', '2027-02-21', 'Åre, Jämtland', 50);

-- Insert Participants
INSERT INTO participant (first_name, last_name, email, phone, role_group, patrol_id) VALUES
                                                                                         ('Benjamin', 'Petterson', 'benjamin.p@example.com', '070-1234567', 'scout/Sjulnäs EFS', 1),
                                                                                         ('Beda', 'Larsson', 'beda.l@example.com', '070-2345678', 'rover/Örebro/lodjuren', 1),
                                                                                         ('Åsa', 'Larsson', 'asa.l@example.com', '070-3456789', 'rover/Örebro/lodjuren', 1),
                                                                                         ('Erik', 'Persson', 'erik.p@example.com', '070-4567890', 'scout/Nacka/vargarna', 3),
                                                                                         ('Lisa', 'Nilsson', 'lisa.n@example.com', '070-5678901', 'rover/Täby/björnarna', 2),
                                                                                         ('Johan', 'Berg', 'johan.b@example.com', '070-6789012', 'scout/Sollentuna', 4),
                                                                                         ('Anna', 'Svensson', 'anna.s@example.com', '070-7890123', 'scout/Sjulnäs EFS', 1),
                                                                                         ('Kalle', 'Andersson', 'kalle.a@example.com', '070-8901234', 'rover/Örebro', 1);

-- Insert Registrations
INSERT INTO registration (event_id, participant_id, status, special_requests) VALUES
                                                                                  (1, 1, 'CONFIRMED', NULL),
                                                                                  (1, 2, 'CONFIRMED', NULL),
                                                                                  (1, 3, 'CONFIRMED', NULL),
                                                                                  (1, 4, 'CONFIRMED', NULL),
                                                                                  (1, 5, 'CONFIRMED', NULL),
                                                                                  (1, 6, 'CONFIRMED', NULL),
                                                                                  (1, 7, 'CONFIRMED', NULL),
                                                                                  (1, 8, 'CONFIRMED', NULL);

-- Insert Participant Allergens
-- Benjamin Petterson har laktos och gluten
INSERT INTO participant_allergen (participant_id, allergen_id) VALUES (1, 1), (1, 2);

-- Beda Larsson har laktos och gluten
INSERT INTO participant_allergen (participant_id, allergen_id) VALUES (2, 1), (2, 2);

-- Åsa Larsson har laktos och gluten
INSERT INTO participant_allergen (participant_id, allergen_id) VALUES (3, 1), (3, 2);

-- Erik Persson har laktos
INSERT INTO participant_allergen (participant_id, allergen_id) VALUES (4, 1);

-- Lisa Nilsson har gluten
INSERT INTO participant_allergen (participant_id, allergen_id) VALUES (5, 2);

-- Johan Berg har nötter (KRITISK!)
INSERT INTO participant_allergen (participant_id, allergen_id) VALUES (6, 3);

-- Anna Svensson har laktos
INSERT INTO participant_allergen (participant_id, allergen_id) VALUES (7, 1);

-- Kalle Andersson har flera allergier
INSERT INTO participant_allergen (participant_id, allergen_id) VALUES (8, 1), (8, 2), (8, 5);