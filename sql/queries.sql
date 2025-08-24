-- Example SQL queries you can run against MySQL

-- All products with category
SELECT p.id, p.name, c.name AS category, p.price, p.quantity
FROM product p JOIN category c ON p.category_id = c.id;

-- Search by name (case-insensitive)
SELECT * FROM product WHERE LOWER(name) LIKE LOWER(CONCAT('%', ? ,'%'));

-- Sum transaction deltas per product
SELECT p.name, SUM(t.delta) AS total_delta
FROM inventory_transaction t
JOIN product p ON p.id = t.product_id
WHERE t.product_id = ?
GROUP BY p.name;
