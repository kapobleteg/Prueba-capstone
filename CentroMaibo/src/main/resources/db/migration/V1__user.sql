CREATE TABLE IF NOT EXISTS "user"
(
    id            SERIAL PRIMARY KEY,
    name          VARCHAR(100) NOT NULL,
    email         VARCHAR(100) NOT NULL UNIQUE,
    phone         VARCHAR(20)  NOT NULL,
    role_id       INTEGER      NOT NULL,
    status        BOOLEAN      NOT NULL,
    password_hash VARCHAR(255),
    activation_key VARCHAR(255),

    CONSTRAINT email_unique UNIQUE (email)
);