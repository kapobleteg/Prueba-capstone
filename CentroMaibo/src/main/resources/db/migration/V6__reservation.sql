CREATE TABLE IF NOT EXISTS reservation
(
    id         SERIAL PRIMARY KEY,
    user_id    INT       NOT NULL,
    box_id     INT       NULL,
    start_date TIMESTAMP NOT NULL,
    end_date   TIMESTAMP NOT NULL,
    status     TEXT      NOT NULL,
    receipt_id INT       NOT NULL,
    psychologist_id INT,

    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES "user" (id) ON DELETE CASCADE,
    CONSTRAINT fk_box FOREIGN KEY (box_id) REFERENCES box (id) ON DELETE CASCADE,
    CONSTRAINT fk_receipt FOREIGN KEY (receipt_id) REFERENCES payment_receipt (id) ON DELETE SET NULL
);