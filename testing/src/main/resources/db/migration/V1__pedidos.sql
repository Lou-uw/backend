CREATE TABLE IF NOT EXISTS pedidos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    fecha DATETIME,
    total FLOAT,
    estado VARCHAR(50),
    forma_pago VARCHAR(30),
    cliente_nombre VARCHAR(100),
    cliente_telefono VARCHAR(50),
    direccion_entrega VARCHAR(300)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;