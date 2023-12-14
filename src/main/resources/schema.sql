CREATE TABLE cryptocurrency (
                                       symbol VARCHAR(255) NOT NULL,
                                       name VARCHAR(255) NOT NULL,
                                       create_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
                                       PRIMARY KEY (symbol)
);

CREATE TABLE batch_job (
                           name VARCHAR(255),
                           lock_until TIMESTAMP,
                           locked_at TIMESTAMP,
                           locked_by VARCHAR(255)
);
