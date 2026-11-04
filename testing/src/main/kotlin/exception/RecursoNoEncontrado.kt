package etec.bdd.exception

class RecursoNoEncontradoException(recurso: String, id: Int) :
    RuntimeException("$recurso no encontrado con el ID: $id")