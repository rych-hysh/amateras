CREATE TABLE IF NOT EXISTS amateras_db.tbl (id int);
CREATE TABLE IF NOT EXISTS amateras_db.running_algorithms (id int primary key, algorithm_id int, simulator_id int, user_uuid varchar(256), is_subscribed boolean);
CREATE TABLE IF NOT EXISTS amateras_db.rates (id int primary key, ask_price float, bid_price float, date datetime);
INSERT IGNORE INTO running_algorithms(id, algorithm_id, simulator_id, user_uuid, is_subscribed) VALUES (0, 0, 0,    null,               0);