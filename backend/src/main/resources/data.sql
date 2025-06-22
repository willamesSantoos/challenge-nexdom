INSERT INTO products (code, description, product_type, supplier_price, stock_quantity) VALUES
('NBK-001', 'Notebook Dell i7 16GB', 'ELECTRONIC', 3500.00, 15),
('SMP-002', 'Smartphone Samsung Galaxy S23', 'ELECTRONIC', 2800.00, 25),
('GEL-003', 'Geladeira Frost Free 500L', 'APPLIANCE', 4200.00, 8),
('LAV-004', 'Lavadora de Roupas 15kg', 'APPLIANCE', 2300.00, 12),
('MES-005', 'Mesa de Escritório', 'FURNITURE', 850.00, 30),
('CAD-006', 'Cadeira Gamer Ergonômica', 'FURNITURE', 1200.00, 18);


INSERT INTO stock_movements (product_id, movement_type, sale_price, quantity, movement_date) VALUES
(1, 'IN', 4000.00, 10, '2025-01-10 09:00:00'),
(1, 'IN', 4100.00, 5, '2025-01-15 14:30:00'),
(2, 'IN', 3200.00, 20, '2025-01-05 11:00:00'),
(2, 'IN', 3300.00, 5, '2025-01-20 16:45:00'),
(3, 'IN', 5000.00, 5, '2025-01-08 10:15:00'),
(3, 'IN', 5100.00, 3, '2025-01-18 13:20:00'),

(1, 'OUT', 4500.00, 3, '2025-01-12 10:30:00'),
(1, 'OUT', 4600.00, 2, '2025-01-17 15:15:00'),
(2, 'OUT', 3500.00, 8, '2025-01-07 14:00:00'),
(2, 'OUT', 3600.00, 7, '2025-06-22 11:30:00'),
(2, 'IN', 3600.00, 4, '2025-06-23 11:30:00'),
(4, 'OUT', 2800.00, 4, '2025-06-14 09:45:00'),
(5, 'OUT', 1000.00, 5, '2025-06-16 16:00:00');