DROP SCHEMA IF EXISTS amateras_db;
CREATE SCHEMA amateras_db;
USE amateras_db;

CREATE USER 'springuser'@'%' identified by 'password';
grant all on amateras_db.* to 'springuser'@'%';
