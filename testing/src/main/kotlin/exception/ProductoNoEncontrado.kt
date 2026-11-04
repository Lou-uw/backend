package etec.bdd.exception

class ProductoNoEncontradoException(id: Int) :
    RuntimeException("Producto no encontrado con el ID: $id")