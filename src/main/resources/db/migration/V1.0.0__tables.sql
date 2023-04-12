CREATE TABLE IF NOT EXISTS amateras_db.tbl (id int);
CREATE TABLE IF NOT EXISTS amateras_db.running_algorithms (id int primary key AUTO_INCREMENT, algorithm_id int, simulator_id int, user_uuid varchar(256), is_subscribed boolean);
CREATE TABLE IF NOT EXISTS amateras_db.rates (id int primary key AUTO_INCREMENT, ask_price float, bid_price float, date datetime);
