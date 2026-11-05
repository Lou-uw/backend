CREATE TABLE IF NOT EXISTS bordes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    precio_base DECIMAL(10,2) NOT NULL DEFAULT 0.00
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO bordes (nombre, precio_base) VALUES
    ('Sin borde', 0.00),
    ('Borde de queso', 1500.00),
    ('Borde de pan', 1000.00);
