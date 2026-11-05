CREATE TABLE IF NOT EXISTS detalle_pedido (
    id INT AUTO_INCREMENT PRIMARY KEY,
    pedido_id INT NOT NULL,
    producto_id INT NOT NULL,
    tamanio_id INT NULL,
    borde_id INT NULL,
    cantidad INT NOT NULL,
    precio_unitario DECIMAL(10,2) NOT NULL,
    precio_borde DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    descripcion VARCHAR(300) NULL,
    CONSTRAINT fk_detalle_pedido
        FOREIGN KEY (pedido_id) REFERENCES pedidos(id) ON DELETE CASCADE,
    CONSTRAINT fk_detalle_producto
        FOREIGN KEY (producto_id) REFERENCES productos(id),
    CONSTRAINT fk_detalle_tamanio
        FOREIGN KEY (tamanio_id) REFERENCES tamanios(id),
    CONSTRAINT fk_detalle_borde
        FOREIGN KEY (borde_id) REFERENCES bordes(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
