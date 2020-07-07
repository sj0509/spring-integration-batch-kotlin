CREATE TABLE ${schema}.TRADE
(
    ID              INT AUTO_INCREMENT PRIMARY KEY,
    DATE            VARCHAR(255) NULL,
    MARKET          VARCHAR(255) NULL,
    TRANSACTIONTYPE VARCHAR(255) NULL,
    PRICE           DOUBLE       NULL,
    AMOUNT          DOUBLE       NULL,
    TOTAL           DOUBLE       NULL,
    FEE             DOUBLE       NULL,
    FEECOIN         VARCHAR(255) NULL,
    JOBID           BIGINT       NOT NULL
)
    ENGINE = MYISAM;

