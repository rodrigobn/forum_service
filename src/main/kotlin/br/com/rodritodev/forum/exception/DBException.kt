package br.com.rodritodev.forum.exception

/**
 * Exceção de erro no banco de dados
 */
class DBException: RuntimeException {
    constructor(message: String?): super(message)
    constructor(message: String?, cause: Throwable?): super(message, cause)
}

class DataIntegrityViolationException(message: String?): RuntimeException(message)