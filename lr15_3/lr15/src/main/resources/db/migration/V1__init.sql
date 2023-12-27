CREATE TABLE flowers
(
    id       serial primary key,
    name     varchar(100),
    flower   varchar(50),
    quantity varchar(20),
    price    integer,
    views    int not null default 0
);

INSERT INTO flowers (id, name, flower, quantity, price, views)
VALUES
    (default, 'Воздушный', 'Тюльпаны, чушпаны', 'Натуральные', 2999, 0),
    (default, 'Гипсофильный', 'Гипсофилы', 'Натуральные', 1500, 0),
    (default, 'Хз', 'Розы', 'Натуральные', 1200, 0);

CREATE TABLE users
(
    username VARCHAR(50)  NOT NULL,
    password VARCHAR(100) NOT NULL,
    name     VARCHAR(20)  NOT NULL,
    enabled  boolean      NOT NULL,
    PRIMARY KEY (username)
);

INSERT INTO users
VALUES ('admin', '$2a$10$dYJ9JcdxtCIc6jnJYNTDFOs1tdPt1te25Gf5JKIEc7uRBvJiSk6JO', 'Лера', true),
       ('user', '$2a$10$zxVS3muLezmSlzipO76OVuUsEPwxBzgYrMMBXu.b383sFiaO.rB5m', 'Не Лера', true);

CREATE TABLE authorities
(
    username  varchar(50) NOT NULL,
    authority varchar(50) NOT NULL,

    CONSTRAINT authorities_idx UNIQUE (username, authority),

    CONSTRAINT authorities_ibfk_1
        FOREIGN KEY (username)
            REFERENCES users (username)
);

INSERT INTO authorities
VALUES ('admin', 'ROLE_ADMIN'),
       ('user', 'ROLE_USER');
