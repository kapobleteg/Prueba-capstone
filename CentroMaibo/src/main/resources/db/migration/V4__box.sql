CREATE TABLE IF NOT EXISTS box
(
    id           SERIAL PRIMARY KEY,
    name         VARCHAR(100)   NOT NULL,
    location     VARCHAR(100)   NOT NULL,
    capacity     INT            NOT NULL,
    availability BOOLEAN        NOT NULL,
    price        INT            NOT NULL,
    time         NUMERIC(10, 2) NOT NULL
);