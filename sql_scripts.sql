--the following script is executed for the BO1 , BO2 and HO databases
--creating databases
CREATE DATABASE IF NOT EXISTS "replace with database name";

USE "replace with database name";

 -- creating product_sales table
CREATE TABLE IF NOT EXISTS product_sales (
  date DATE,
  region VARCHAR(255),
  product VARCHAR(255),
  qty INT,
  cost DECIMAL(10,2),
  amount DECIMAL(10,2),
  tax DECIMAL(10,2),
  total DECIMAL(10,2)
);

-- inserting initial data


INSERT INTO product_sales (date, region, product, qty, cost, amount, tax, total)
VALUES ('2024-04-01', 'East', 'Paper', 73, 12.95, 945.35, 66.17, 1011.52),
('2024-04-01', 'West', 'Paper', 33, 12.95, 427.35, 29.91, 457.26),
('2024-04-02', 'East', 'Pens', 14, 2.19, 30.66, 2.15, 32.81),
('2024-04-02', 'West', 'Pens', 40, 2.19, 87.60, 6.13, 93.73),
('2024-04-03', 'East', 'Paper', 21, 12.95, 271.95, 19.04, 290.99),
('2024-04-03', 'West', 'Paper', 10, 12.95, 129.50, 9.07, 138.57);


--creating the method executed in the trigger

CREATE PROCEDURE capture_changes(IN query VARCHAR(2000))
BEGIN
INSERT INTO product_sales_changes_log (query, created_at)
VALUES (query, NOW());
END

-- trigger

CREATE TRIGGER product_sales_insert_trigger
AFTER INSERT ON product_sales
FOR EACH ROW
BEGIN
DECLARE sql_stmt VARCHAR(2000);
SET sql_stmt = CONCAT('INSERT INTO product_sales (date, region, product, qty, cost, amount, tax, total) VALUES (\'', NEW.date, '\', \'', NEW.region, '\', \'', NEW.product, '\', ', NEW.qty, ', ', NEW.cost, ', ', NEW.amount, ', ', NEW.tax, ', ', NEW.total, ');');
CALL capture_product_sales_changes(sql_stmt);
END

CREATE TRIGGER product_sales_update_trigger
AFTER UPDATE ON product_sales
FOR EACH ROW
BEGIN
DECLARE sql_stmt VARCHAR(2000);
SET sql_stmt = CONCAT('UPDATE product_sales SET date = \'', NEW.date, '\', region = \'', NEW.region, '\', product = \'', NEW.product, '\', qty = ', NEW.qty, ', cost = ', NEW.cost, ', amount = ', NEW.amount, ', tax = ', NEW.tax, ', total = ', NEW.total, ' WHERE date = \'', OLD.date, '\' AND region = \'', OLD.region, '\' AND product = \'', OLD.product, '\';');
CALL capture_product_sales_changes(sql_stmt);
END

CREATE TRIGGER product_sales_delete_trigger
AFTER DELETE ON product_sales
FOR EACH ROW
BEGIN
DECLARE sql_stmt VARCHAR(2000);
SET sql_stmt = CONCAT('DELETE FROM product_sales WHERE date = \'', OLD.date, '\' AND region = \'', OLD.region, '\' AND product = \'', OLD.product, '\';');
CALL capture_product_sales_changes(sql_stmt);
END


