CREATE TABLE IF NOT EXISTS Role
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(50) NOT NULL,
    permissions TEXT[] NOT NULL
);