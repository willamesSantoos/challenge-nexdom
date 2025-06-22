INSERT INTO products (code, description, product_type, supplier_price, stock_quantity) VALUES
('NBK-001', 'Notebook Dell i7 16GB', 'ELECTRONIC', 3500.00, 15),
('SMP-002', 'Smartphone Samsung Galaxy S23', 'ELECTRONIC', 2800.00, 25),
('GEL-003', 'Geladeira Frost Free 500L', 'APPLIANCE', 4200.00, 8),
('LAV-004', 'Lavadora de Roupas 15kg', 'APPLIANCE', 2300.00, 12),
('MES-005', 'Mesa de Escritório', 'FURNITURE', 850.00, 30),
('CAD-006', 'Cadeira Gamer Ergonômica', 'FURNITURE', 1200.00, 18),
('TVS-007', 'Smart TV 55" 4K', 'ELECTRONIC', 3100.00, 10),
('MIC-008', 'Micro-ondas 30L', 'APPLIANCE', 600.00, 20),
('FRI-009', 'Frigobar 90L', 'APPLIANCE', 750.00, 14),
('ARM-010', 'Armário de Cozinha 3 portas', 'FURNITURE', 980.00, 9),
('IMP-011', 'Impressora Multifuncional HP', 'ELECTRONIC', 680.00, 22),
('TBL-012', 'Tablet Android 10"', 'ELECTRONIC', 1300.00, 16),
('CAM-013', 'Câmera de Segurança Wi-Fi', 'ELECTRONIC', 420.00, 35),
('ASP-014', 'Aspirador de Pó Vertical', 'APPLIANCE', 450.00, 17),
('SOF-015', 'Sofá 3 Lugares Retrátil', 'FURNITURE', 2100.00, 6),
('EST-016', 'Estante para Livros', 'FURNITURE', 740.00, 11),
('CLM-017', 'Climatizador de Ar Portátil', 'APPLIANCE', 980.00, 13),
('HED-018', 'Headset Gamer com Microfone', 'ELECTRONIC', 390.00, 27),
('WCH-019', 'Smartwatch Android', 'ELECTRONIC', 860.00, 19),
('BLC-020', 'Balcão de Cozinha com Gavetas', 'FURNITURE', 1290.00, 7);


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