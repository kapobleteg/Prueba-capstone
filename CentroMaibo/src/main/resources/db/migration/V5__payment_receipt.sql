CREATE TABLE IF NOT EXISTS payment_receipt
(
    id       SERIAL PRIMARY KEY,
    attached TEXT           NOT NULL,
    amount   NUMERIC(10, 2) NOT NULL,
    date     TIMESTAMP      NOT NULL,
    status   TEXT           NOT NULL,
    comment  TEXT
);