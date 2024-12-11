CREATE TABLE IF NOT EXISTS Profile
(
    id             SERIAL PRIMARY KEY,
    user_id        INTEGER NOT NULL REFERENCES "user" (id) ON DELETE CASCADE,
    specialty      VARCHAR(100),
    certifications TEXT[]
);