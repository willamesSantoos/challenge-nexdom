INSERT INTO products (code, description, product_type, supplier_price, stock_quantity) VALUES
('NBK-006', 'Notebook Dell i7 16GB', 'ELECTRONIC', 3500.00, 0),
('SMP-002', 'Smartphone Samsung Galaxy S23', 'ELECTRONIC', 2800.00, 0),
('GEL-003', 'Geladeira Frost Free 500L', 'APPLIANCE', 4200.00, 0);


--INSERT INTO stock_movements (product_id, movement_type, sale_price, quantity, movement_date) VALUES
--(1, 'IN', 3500.00, 10, '2025-06-20 09:00:00'),
--(1, 'OUT', 4100.00, 5, '2025-06-22 14:30:00'),
--(1, 'IN', 3500.00, 20, '2025-06-22 11:00:00'),
--(2, 'IN', 2800.00, 5, '2025-06-17 16:45:00'),
--(3, 'IN', 4200.00, 5, '2025-06-18 10:15:00'),
--(3, 'OUT', 5100.00, 3, '2025-06-19 13:20:00');