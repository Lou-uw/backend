CREATE TABLE IF NOT EXISTS pedidos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    fecha DATETIME,
    total DECIMAL(10,2),
    estado VARCHAR(100),
    forma_pago VARCHAR(30),
    direccion_entrega VARCHAR(300),
    cliente_id INT,
    FOREIGN KEY (cliente_id) REFERENCES clientes(id)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;