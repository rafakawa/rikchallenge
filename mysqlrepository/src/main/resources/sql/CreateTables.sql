use rikchallenge;

CREATE TABLE payment_order (
    id                VARCHAR (50)           PRIMARY KEY,
    client_id         VARCHAR (255)                     ,
    buyer_name        VARCHAR (255)                     ,
    buyer_email       VARCHAR (255)                     ,
    buyer_cpf         VARCHAR (15)                      ,
    payment_amount    DOUBLE                            ,
    payment_type      VARCHAR (50)                      ,
    card_holder_name  VARCHAR (255)                     ,
    card_number       VARCHAR (16)                      ,
    card_expiration_date VARCHAR (8)                    ,
    card_cvv          VARCHAR (4)                       ,
    order_date        DATETIME
);

CREATE INDEX payment_order_clientId_buyerCpf
ON payment_order(client_id,buyer_cpf);

CREATE TABLE payment_order_status (
    id                VARCHAR (50)           PRIMARY KEY,
    order_id          VARCHAR (50)                      ,
    order_status      VARCHAR (50)                      ,
    payload_type      VARCHAR (50)                      ,
    payload           VARCHAR (255)                     ,
    status_date       DATETIME                          ,
    FOREIGN KEY (order_id) REFERENCES payment_order(id)
);