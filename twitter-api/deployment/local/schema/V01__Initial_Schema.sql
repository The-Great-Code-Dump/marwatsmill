CREATE OR REPLACE FUNCTION update_timestamp() RETURNS TRIGGER AS $$

CREATE TABLE IF NOT EXISTS tweet (
    id bigint NOT NULL,
    author_id bigint NOT NULL,
    conversation_id bigint NOT NULL,
    created_at varchar(255) DEFAULT NULL,
    lang varchar(255) DEFAULT NULL,
    possible_sensitive boolean DEFAULT FALSE,
    reply_settings varchar(255) DEFAULT NULL,
    source varchar(255) DEFAULT NULL,
    text varchar(255) DEFAULT NULL,
    last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);

