-- Seed categories
INSERT INTO category (id, name) VALUES (1, 'camera') ON DUPLICATE KEY UPDATE name=name;
INSERT INTO category (id, name) VALUES (2, 'thermostats') ON DUPLICATE KEY UPDATE name=name;

-- Seed products with category mapping
INSERT INTO product (id, name, category_id, price, quantity) VALUES
 (1, 'Apple camera', 1, 999.00, 10)
ON DUPLICATE KEY UPDATE name=name;

INSERT INTO product (id, name, category_id, price, quantity) VALUES
 (2, 'Sony camera', 1, 799.00, 12)
ON DUPLICATE KEY UPDATE name=name;

INSERT INTO product (id, name, category_id, price, quantity) VALUES
 (3, 'Samsung camera', 1, 699.00, 15)
ON DUPLICATE KEY UPDATE name=name;

INSERT INTO product (id, name, category_id, price, quantity) VALUES
 (4, 'Foxconn Thermostats', 2, 199.00, 30)
ON DUPLICATE KEY UPDATE name=name;

INSERT INTO product (id, name, category_id, price, quantity) VALUES
 (5, 'Qualcomm Thermostats', 2, 249.00, 25)
ON DUPLICATE KEY UPDATE name=name;
