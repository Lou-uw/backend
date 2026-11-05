CREATE TABLE IF NOT EXISTS tamanios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(30) NOT NULL,
    porciones INT,
    precio_clasica FLOAT NOT NULL,
    precio_especial FLOAT NOT NULL,
    precio_borde FLOAT NOT NULL
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;