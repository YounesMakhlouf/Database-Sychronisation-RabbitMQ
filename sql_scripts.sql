-- the following script is executed for the BO1 , BO2 and HO databases
-- Replace "database_name" with your actual database name before running this script

-- creating databases
# CREATE DATABASE IF NOT EXISTS "database_name";
# USE "database_name";


CREATE USER IF NOT EXISTS 'sqluser'@'localhost' IDENTIFIED BY 'sqluserpw';
GRANT USAGE ON *.* TO 'sqluser'@'localhost';
GRANT ALL PRIVILEGES ON *.* TO 'sqluser'@'localhost';

-- creating product_sales table
CREATE TABLE product_sales
(
    id       INT          NOT NULL AUTO_INCREMENT,
    DATE     DATE         NOT NULL,
    REGION   VARCHAR(30)  NOT NULL,
    PRODUCT  VARCHAR(255) NOT NULL,
    QUANTITY INT,
    COST     DECIMAL(10, 2),
    AMOUNT   DECIMAL(10, 2),
    TAX      DECIMAL(10, 2),
    TOTAL    DECIMAL(10, 2),
    PRIMARY KEY (ID)
);

INSERT INTO product_sales (date, region, product, quantity, cost, amount, tax, total)
VALUES ('2024-04-01', 'East', 'Paper', 73, 12.95, 945.35, 66.17, 1011.52),
       ('2024-04-01', 'West', 'Paper', 33, 12.95, 427.35, 29.91, 457.26),
       ('2024-04-02', 'East', 'Pens', 14, 2.19, 30.66, 2.15, 32.81),
       ('2024-04-02', 'West', 'Pens', 40, 2.19, 87.60, 6.13, 93.73),
       ('2024-04-03', 'East', 'Paper', 21, 12.95, 271.95, 19.04, 290.99),
       ('2024-04-03', 'West', 'Paper', 10, 12.95, 129.50, 9.07, 138.57);

CREATE TABLE IF NOT EXISTS product_sales_changes_log
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    query      VARCHAR(2000),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE PROCEDURE capture_changes(IN s_query VARCHAR(2000))
BEGIN
    INSERT INTO product_sales_changes_log (query)
    VALUES (s_query);
END;


-- trigger

DELIMITER $$
CREATE TRIGGER product_sales_insert_trigger
    AFTER INSERT
    ON product_sales
    FOR EACH ROW
BEGIN
    CALL capture_changes(CONCAT(
            'INSERT INTO product_sales (date, region, product, quantity, cost, amount, tax, total) VALUES (''',
            NEW.date, ''', ''', NEW.region, ''', ''', NEW.product, ''', ', NEW.quantity, ', ', NEW.cost, ', ',
            NEW.amount, ', ', NEW.tax, ', ', NEW.total, ');'));
END$$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER product_sales_update_trigger
    AFTER UPDATE
    ON product_sales
    FOR EACH ROW
BEGIN
    DECLARE sql_stmt VARCHAR(2000);
    SET sql_stmt =
            CONCAT('UPDATE product_sales SET date = \'', NEW.date, '\', region = \'', NEW.region, '\', product = \'',
                   NEW.product, '\', quantity = ', NEW.quantity, ', cost = ', NEW.cost, ', amount = ', NEW.amount,
                   ', tax = ', NEW.tax, ', total = ', NEW.total, ' WHERE date = \'', OLD.date, '\' AND region = \'',
                   OLD.region, '\' AND product = \'', OLD.product, '\';');
    CALL capture_changes(sql_stmt);
END$$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER product_sales_delete_trigger
    AFTER DELETE
    ON product_sales
    FOR EACH ROW
BEGIN
    DECLARE sql_stmt VARCHAR(2000);
    SET sql_stmt = CONCAT('DELETE FROM product_sales WHERE date = \'', OLD.date, '\' AND region = \'', OLD.region,
                          '\' AND product = \'', OLD.product, '\';');
    CALL capture_changes(sql_stmt);
END$$
DELIMITER ;


