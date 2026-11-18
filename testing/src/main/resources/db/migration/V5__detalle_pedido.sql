CREATE TABLE IF NOT EXISTS detalle_pedido (
    id INT AUTO_INCREMENT PRIMARY KEY,
    pedido_id INT NOT NULL,
    producto_id INT NOT NULL,
    tamanio_id INT,
    con_borde TINYINT(1) DEFAULT 0,
    cantidad INT,
    precio_unitario FLOAT,
    precio_borde FLOAT DEFAULT 0,
    FOREIGN KEY (pedido_id) REFERENCES pedidos(id) ON DELETE CASCADE,
    FOREIGN KEY (producto_id) REFERENCES productos(id),
    FOREIGN KEY (tamanio_id) REFERENCES tamanios(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;