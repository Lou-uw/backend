CREATE TABLE IF NOT EXISTS productos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(500) NULL,
    imagen VARCHAR(500) NULL,
    precio_base DECIMAL(10,2) NOT NULL,
    stock INT NULL,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    categoria_id INT NOT NULL,
    CONSTRAINT fk_productos_categoria
        FOREIGN KEY (categoria_id) REFERENCES categorias(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
