INSERT INTO cryptocurrency (symbol, name)
VALUES ('BTCUSDT', 'Bitcoin'),
       ('ETHUSDT', 'Ethereum'),
       ('USDT', 'USDT');


INSERT INTO users (email, password_hash)
VALUES ('stephan@gmail.com', 'passwordHash1'),
    ('tom@gmail.com', 'passwordHash2');

INSERT INTO wallet (user_id, symbol, balance)
VALUES ((SELECT id FROM users WHERE email = 'stephan@gmail.com'),
        'BTCUSDT', 0.0),
       ((SELECT id FROM users WHERE email = 'stephan@gmail.com'),
        'ETHUSDT', 0.0),
       ((SELECT id FROM users WHERE email = 'stephan@gmail.com'),
        'USDT', 50000.0);


INSERT INTO wallet (user_id, symbol, balance)
VALUES ((SELECT id FROM users WHERE email = 'tom@gmail.com'),
        'BTCUSDT', 0.0),
       ((SELECT id FROM users WHERE email = 'tom@gmail.com'),
        'ETHUSDT', 0.0),
       ((SELECT id FROM users WHERE email = 'tom@gmail.com'),
        'USDT', 50000.0);
