
INSERT IGNORE INTO positions(id, pair, simulator_id, is_ask, at_rate, lots, algorithm_id, at_date, is_settled) VALUES (0, "USD/JPY", 1, 1, 125 + 15* rand(), 3, 1,  '2019-10-01 12:35:00', 0);
INSERT IGNORE INTO positions(id, pair, simulator_id, is_ask, at_rate, lots, algorithm_id, at_date, is_settled) VALUES (1, "USD/JPY", 1, 1, 125 + 15* rand(), 2, 1,  '2019-10-01 12:20:00', 1);
INSERT IGNORE INTO positions(id, pair, simulator_id, is_ask, at_rate, lots, algorithm_id, at_date, is_settled) VALUES (2, "USD/JPY", 1, 0, 125 + 15* rand(), 4, 0,  '2019-10-01 12:05:00', 1);
INSERT IGNORE INTO positions(id, pair, simulator_id, is_ask, at_rate, lots, algorithm_id, at_date, is_settled) VALUES (3, "USD/JPY", 1, 0, 125 + 15* rand(), 1, 1,  '2019-10-01 11:50:00', 1);
INSERT IGNORE INTO positions(id, pair, simulator_id, is_ask, at_rate, lots, algorithm_id, at_date, is_settled) VALUES (4, "USD/JPY", 1, 1, 125 + 15* rand(), 2, 1,  '2019-10-01 12:35:00', 1);
INSERT IGNORE INTO positions(id, pair, simulator_id, is_ask, at_rate, lots, algorithm_id, at_date, is_settled) VALUES (5, "USD/JPY", 2, 0, 125 + 15* rand(), 2, 3,  '2019-10-01 12:30:00', 0);
INSERT IGNORE INTO positions(id, pair, simulator_id, is_ask, at_rate, lots, algorithm_id, at_date, is_settled) VALUES (6, "USD/JPY", 2, 0, 125 + 15* rand(), 2, 2,  '2019-10-01 12:10:00', 1);
INSERT IGNORE INTO positions(id, pair, simulator_id, is_ask, at_rate, lots, algorithm_id, at_date, is_settled) VALUES (7, "USD/JPY", 2, 1, 125 + 15* rand(), 4, 1,  '2019-10-01 11:30:00', 1);
INSERT IGNORE INTO positions(id, pair, simulator_id, is_ask, at_rate, lots, algorithm_id, at_date, is_settled) VALUES (8, "USD/JPY", 3, 1, 125 + 15* rand(), 3, 2,  '2019-10-01 12:30:00', 0);
INSERT IGNORE INTO positions(id, pair, simulator_id, is_ask, at_rate, lots, algorithm_id, at_date, is_settled) VALUES (9, "USD/JPY", 3, 0, 125 + 15* rand(), 4, 1,  '2019-10-01 12:00:00', 1);
INSERT IGNORE INTO positions(id, pair, simulator_id, is_ask, at_rate, lots, algorithm_id, at_date, is_settled) VALUES (10, "USD/JPY", 4, 0, 125 + 15* rand(), 4, 0, '2019-10-01 11:30:00', 0);
INSERT IGNORE INTO positions(id, pair, simulator_id, is_ask, at_rate, lots, algorithm_id, at_date, is_settled) VALUES (11, "USD/JPY", 4, 1, 125 + 15* rand(), 2, 2, '2019-10-01 10:35:00', 1);
INSERT IGNORE INTO positions(id, pair, simulator_id, is_ask, at_rate, lots, algorithm_id, at_date, is_settled) VALUES (12, "USD/JPY", 5, 0, 125 + 15* rand(), 1, 1, '2019-10-01 12:30:00', 0);

INSERT IGNORE INTO simulators(id, simulator_name, user_uuid, is_running) VALUES (0, "Simulator A", "1893d2b7-ed66-44b2-a133-35a45f0a4947", 1);
INSERT IGNORE INTO simulators(id, simulator_name, user_uuid, is_running) VALUES (1, "Simulator B", "TestUser2Uuid", 1);
INSERT IGNORE INTO simulators(id, simulator_name, user_uuid, is_running) VALUES (2, "Simulator C", "TestUser3Uuid", 1);
INSERT IGNORE INTO simulators(id, simulator_name, user_uuid, is_running) VALUES (3, "Simulator D", "1893d2b7-ed66-44b2-a133-35a45f0a4947", 0);
INSERT IGNORE INTO simulators(id, simulator_name, user_uuid, is_running) VALUES (4, "Simulator E", "TestUser2Uuid", 0);
INSERT IGNORE INTO simulators(id, simulator_name, user_uuid, is_running) VALUES (5, "Simulator F", "1893d2b7-ed66-44b2-a133-35a45f0a4947", 1);
INSERT IGNORE INTO simulators(id, simulator_name, user_uuid, is_running) VALUES (6, "Simulator G", "1893d2b7-ed66-44b2-a133-35a45f0a4947", 1);

INSERT IGNORE INTO running_algorithms(id, algorithm_id, simulator_id, user_uuid, is_subscribed) VALUES (0, 0, 0,    null,               0);
INSERT IGNORE INTO running_algorithms(id, algorithm_id, simulator_id, user_uuid, is_subscribed) VALUES (1, 1, 0,    null,               1);
INSERT IGNORE INTO running_algorithms(id, algorithm_id, simulator_id, user_uuid, is_subscribed) VALUES (2, 0, null, "TestUser1Uuid",    1);
INSERT IGNORE INTO running_algorithms(id, algorithm_id, simulator_id, user_uuid, is_subscribed) VALUES (3, 1, 1,    null,               1);
INSERT IGNORE INTO running_algorithms(id, algorithm_id, simulator_id, user_uuid, is_subscribed) VALUES (4, 2, 2,    null,               1);
INSERT IGNORE INTO running_algorithms(id, algorithm_id, simulator_id, user_uuid, is_subscribed) VALUES (5, 2, 5,    null,               1);
INSERT IGNORE INTO running_algorithms(id, algorithm_id, simulator_id, user_uuid, is_subscribed) VALUES (6, 1, null, "TestUser2Uuid",    1);

INSERT IGNORE INTO positions(id, pair,      simulator_id,   is_ask,     lots,   algorithm_id,   got_date,               got_rate,           is_settled) VALUES
                            (33, "USD/JPY", 2,              true,       1,      1,              '2019-10-01 12:30:00',  125 + 15* rand(),   0);

INSERT IGNORE INTO positions(id, pair,      simulator_id,   is_ask,     lots,   algorithm_id,   got_date,               got_rate,           settled_date,           settled_rate,       is_settled) VALUES
                            (35, "USD/JPY", 2,              true,       1,      1,              '2019-10-01 12:30:00',  125 + 15* rand(),   '2019-10-01 13:30:00',  125 + 15* rand(),   true);