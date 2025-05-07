INSERT INTO brands (name) VALUES
('A'),
('B'),
('C'),
('D'),
('E'),
('F'),
('G'),
('H'),
('I');

INSERT INTO categories (name) VALUES
('TOP'),
('OUTER'),
('BOTTOM'),
('SHOES'),
('BAG'),
('HAT'),
('SOCKS'),
('ACCESSORY');

INSERT INTO products (name, price, category_id, brand_id) VALUES
-- Brand A products
('TOP', 11200, 1, 1),
('OUTER', 5500, 2, 1),
('BOTTOM', 4200, 3, 1),
('SHOES', 9000, 4, 1),
('BAG', 2000, 5, 1),
('HAT', 1700, 6, 1),
('SOCKS', 1800, 7, 1),
('ACCESSORY', 2300, 8, 1),

-- Brand B products
('TOP', 10500, 1, 2),
('OUTER', 5900, 2, 2),
('BOTTOM', 3800, 3, 2),
('SHOES', 9100, 4, 2),
('BAG', 2100, 5, 2),
('HAT', 2000, 6, 2),
('SOCKS', 2000, 7, 2),
('ACCESSORY', 2200, 8, 2),

-- Brand C products
('TOP', 10000, 1, 3),
('OUTER', 6200, 2, 3),
('BOTTOM', 3300, 3, 3),
('SHOES', 9200, 4, 3),
('BAG', 2200, 5, 3),
('HAT', 1900, 6, 3),
('SOCKS', 2200, 7, 3),
('ACCESSORY', 2100, 8, 3),

-- Brand D products
('TOP', 10100, 1, 4),
('OUTER', 5100, 2, 4),
('BOTTOM', 3000, 3, 4),
('SHOES', 9500, 4, 4),
('BAG', 2500, 5, 4),
('HAT', 1500, 6, 4),
('SOCKS', 2400, 7, 4),
('ACCESSORY', 2000, 8, 4),

-- Brand E products
('TOP', 10700, 1, 5),
('OUTER', 5000, 2, 5),
('BOTTOM', 3800, 3, 5),
('SHOES', 9900, 4, 5),
('BAG', 2300, 5, 5),
('HAT', 1800, 6, 5),
('SOCKS', 2100, 7, 5),
('ACCESSORY', 2100, 8, 5),

-- Brand F products
('TOP', 11200, 1, 6),
('OUTER', 7200, 2, 6),
('BOTTOM', 4000, 3, 6),
('SHOES', 9300, 4, 6),
('BAG', 2100, 5, 6),
('HAT', 1600, 6, 6),
('SOCKS', 2300, 7, 6),
('ACCESSORY', 1900, 8, 6),

-- Brand G products
('TOP', 10500, 1, 7),
('OUTER', 5800, 2, 7),
('BOTTOM', 3900, 3, 7),
('SHOES', 9000, 4, 7),
('BAG', 2200, 5, 7),
('HAT', 1700, 6, 7),
('SOCKS', 2100, 7, 7),
('ACCESSORY', 2000, 8, 7),

-- Brand H products
('TOP', 10800, 1, 8),
('OUTER', 6300, 2, 8),
('BOTTOM', 3100, 3, 8),
('SHOES', 9700, 4, 8),
('BAG', 2100, 5, 8),
('HAT', 1600, 6, 8),
('SOCKS', 2000, 7, 8),
('ACCESSORY', 2000, 8, 8),

-- Brand I products
('TOP', 11400, 1, 9),
('OUTER', 6700, 2, 9),
('BOTTOM', 3200, 3, 9),
('SHOES', 9500, 4, 9),
('BAG', 2400, 5, 9),
('HAT', 1700, 6, 9),
('SOCKS', 1700, 7, 9),
('ACCESSORY', 2400, 8, 9);