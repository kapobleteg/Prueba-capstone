CREATE TABLE IF NOT EXISTS bank_info
    (
        id  SERIAL PRIMARY KEY ,
        destiny_name VARCHAR(100),
        bank VARCHAR(100),
        rut VARCHAR(11),
        account_type VARCHAR(100),
        account_number VARCHAR(100),
        amount_transfer NUMERIC(100),
        reason_transfer VARCHAR(100),
        user_id INT,
   CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES "user" (id) ON DELETE CASCADE
)