-- Ajustes del checkout: notas, delivery obligatorio y combos en el detalle.
-- tipo_entrega no se agrega: el negocio es solo delivery.

ALTER TABLE pedidos
    ADD COLUMN IF NOT EXISTS notas VARCHAR(300) NULL;

ALTER TABLE detalle_pedido
    ADD COLUMN IF NOT EXISTS combo_id INT NULL;

ALTER TABLE detalle_pedido
    ADD CONSTRAINT fk_detalle_combo
    FOREIGN KEY (combo_id) REFERENCES combos(id) ON DELETE SET NULL;
