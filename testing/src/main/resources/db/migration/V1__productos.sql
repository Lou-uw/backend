CREATE TABLE IF NOT EXISTS productos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100),
    categoria_id INT NOT NULL,
    tipo_sabor_id INT NULL,
    precio DECIMAL(10,2) NULL,
    stock INT,
    activo BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (categoria_id) REFERENCES categorias(id),
    FOREIGN KEY (tipo_sabor_id) REFERENCES tipos_sabor(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;