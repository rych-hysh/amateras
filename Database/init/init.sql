DROP SCHEMA IF EXISTS forex_rate;
CREATE SCHEMA forex_rate;
USE forex_rate;

CREATE USER 'springuser'@'%' identified by 'password';
grant all on forex_rate.* to 'springuser'@'%';
