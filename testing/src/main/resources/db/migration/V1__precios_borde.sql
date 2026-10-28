CREATE TABLE IF NOT EXISTS precios_borde (
    id INT AUTO_INCREMENT PRIMARY KEY,
    borde_id INT NOT NULL,
    tamanio_id INT NOT NULL,
    precio DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (borde_id) REFERENCES bordes(id),
    FOREIGN KEY (tamanio_id) REFERENCES tamanios(id)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;