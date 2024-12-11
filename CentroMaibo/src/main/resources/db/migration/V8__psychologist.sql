CREATE TABLE IF NOT EXISTS psychologist
(
    id            SERIAL PRIMARY KEY,
    name          VARCHAR(100) NOT NULL,
    user_id       INT NOT NULL,

    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES "user" (id) ON DELETE CASCADE
);