CREATE TABLE IF NOT EXISTS productos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    categoria_id INT NOT NULL,
    precio FLOAT,
    stock INT,
    activo TINYINT(1) DEFAULT 1,
    FOREIGN KEY (categoria_id) REFERENCES categorias(id) ON DELETE CASCADE
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;