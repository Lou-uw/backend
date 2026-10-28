CREATE TABLE IF NOT EXISTS precios_pizza (
    id INT AUTO_INCREMENT PRIMARY KEY,
    tipo_sabor_id INT NOT NULL,
    tamanio_id INT NOT NULL,
    precio DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (tipo_sabor_id) REFERENCES tipos_sabor(id),
    FOREIGN KEY (tamanio_id) REFERENCES tamanios(id)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;