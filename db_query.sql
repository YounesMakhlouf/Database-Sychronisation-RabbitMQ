create database sales;
use sales;

CREATE USER 'sqluser'@'localhost' IDENTIFIED BY 'sqluserpw';

GRANT USAGE ON *.* TO 'sqluser'@'localhost';
GRANT ALL PRIVILEGES ON sales.* TO 'sqluser'@'localhost';

CREATE TABLE product_sales (
        id INT NOT NULL AUTO_INCREMENT,
		DATE DATE NOT NULL,
        REGION VARCHAR(30) NOT NULL,
        PRODUCT VARCHAR(255) NOT NULL,
        QUANTITY INT,
        COST DECIMAL(10,2),
        AMOUNT DECIMAL(10,2),
        TAX DECIMAL(10,2),
        TOTAL DECIMAL(10,2),
        PRIMARY KEY (ID)
    );

INSERT INTO product_sales
VALUES (default, '2024-04-01', 'East', 'Paper', 73, 12.95, 945.35, 66.17, 1011.52),
       (default, '2024-04-01', 'West', 'Paper', 33, 12.95, 427.35, 29.91, 457.26),
       (default, '2024-04-02', 'East', 'Pens', 14, 2.19, 30.66, 2.15, 32.81),
       (default, '2024-04-02', 'West', 'Pens', 40, 2.19, 87.60, 6.13, 93.73),
       (default, '2024-04-03', 'East', 'Paper', 21, 12.95, 271.95, 19.04, 290.99),
       (default, '2024-04-03', 'West', 'Paper', 10, 12.95, 129.50, 9.07, 138.57);


SELECT * FROM product_sales;