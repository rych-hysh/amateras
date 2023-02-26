DROP SCHEMA IF EXISTS forex_rate;
CREATE SCHEMA forex_rate;
USE forex_rate;

DROP TABLE IF EXISTS rates;
CREATE TABLE rates
(
    id          INT primary key,
    datetime    DATETIME,
    open        FLOAT,
    close       FLOAT,
    low         FLOAT,
    high        FLOAT
);

INSERT INTO rates (id, datetime, open, close, low, high) VALUES (1, '1900-4-1 10:00:00', 100.0, 103.4, 98.4, 105.2);
INSERT INTO rates (id, datetime, open, close, low, high) VALUES (2, '1900-4-1 10:05:00', 103.0, 105.2, 98.6, 106.1);
INSERT INTO rates (id, datetime, open, close, low, high) VALUES (3, '1900-4-1 10:10:00', 105.3, 103.5, 102.0, 105.8);
INSERT INTO rates (id, datetime, open, close, low, high) VALUES (4, '1900-4-1 10:15:00', 103.7, 99.4, 98.2, 104.0);