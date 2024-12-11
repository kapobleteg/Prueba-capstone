CREATE TABLE IF NOT EXISTS box_image_urls
(
    box_id     BIGINT NOT NULL,
    image_urls TEXT,
    FOREIGN KEY (box_id) REFERENCES box (id) ON DELETE CASCADE
);