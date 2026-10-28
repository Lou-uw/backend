CREATE TABLE IF NOT EXISTS detalle_pedido (
    id INT AUTO_INCREMENT PRIMARY KEY,
    pedido_id INT,
    producto_id INT,
    tamanio_id INT NULL,
    borde_id INT NULL,
    cantidad INT,
    precio_unitario DECIMAL(10,2),
    precio_borde DECIMAL(10,2) DEFAULT 0.00,
    descripcion TEXT,
    FOREIGN KEY (pedido_id) REFERENCES pedidos(id),
    FOREIGN KEY (producto_id) REFERENCES productos(id),
    FOREIGN KEY (tamanio_id) REFERENCES tamanios(id),
    FOREIGN KEY (borde_id) REFERENCES bordes(id)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;