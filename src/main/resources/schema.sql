CREATE TABLE cryptocurrency
(
    symbol    VARCHAR(100)                        NOT NULL,
    name      VARCHAR(100)                        NOT NULL,
    create_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    PRIMARY KEY (symbol)
);

CREATE TABLE users
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    email      VARCHAR(50) UNIQUE                  NOT NULL,
    password   VARCHAR(255)                        NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL
);

CREATE TABLE wallet
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    user_id    INT                                 NOT NULL,
    balance    DECIMAL(15, 6)                      NOT NULL,
    symbol     VARCHAR(100)                        NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (symbol) REFERENCES cryptocurrency (symbol)
);


CREATE TABLE batch_job
(
    name       VARCHAR(255),
    lock_until TIMESTAMP,
    locked_at  TIMESTAMP,
    locked_by  VARCHAR(255)
);
