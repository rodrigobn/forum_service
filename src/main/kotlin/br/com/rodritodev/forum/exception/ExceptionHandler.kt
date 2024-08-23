package br.com.rodritodev.forum.exception

import br.com.rodritodev.forum.dto.ErrorView
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

/**
 * Classe de tratamento de exceções
 * @see RestControllerAdvice
 */
@RestControllerAdvice
class ExceptionHandler {

    /**
     * Trata exceções do tipo NotFoundException
     * @param exception Exceção lançada
     * @param request Requisição HTTP
     * @return View de erro
     */
    @ExceptionHandler(NotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleNotFound(exception: NotFoundException, request: HttpServletRequest): ErrorView {
        return ErrorView(
            status = HttpStatus.NOT_FOUND.value(),
            error = HttpStatus.NOT_FOUND.name,
            message = exception.message,
            path = request.servletPath
        )
    }

    /**
     * Trata exceções do tipo Exception
     * @param exception Exceção lançada
     * @param request Requisição HTTP
     * @return View de erro
     */
    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleServerError(exception: Exception, request: HttpServletRequest): ErrorView {
        return ErrorView(
            status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
            error = HttpStatus.INTERNAL_SERVER_ERROR.name,
            message = exception.message,
            path = request.servletPath
        )
    }

    /**
     * Trata exceções do bean validation do tipo MethodArgumentNotValidException
     * @param exception Exceção lançada
     * @param request Requisição HTTP
     * @return View de erro
     */
    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handlerValidationError(exception: MethodArgumentNotValidException, request: HttpServletRequest): ErrorView {
        val errorMessage = HashMap<String, String>()
        exception.bindingResult.fieldErrors.forEach { error ->
            errorMessage[error.field] = error.defaultMessage ?: ""
        }
        return ErrorView(
            status = HttpStatus.BAD_REQUEST.value(),
            error = HttpStatus.BAD_REQUEST.name,
            message = errorMessage.toString(),
            path = request.servletPath
        )
    }

    /**
     * Trata exceções do tipo UsuarioJaCadastradoException
     * @param exception Exceção lançada
     * @param request Requisição HTTP
     * @return View de erro
     */
    @ExceptionHandler(UsuarioJaCadastradoException::class)
    @ResponseStatus(HttpStatus.CONFLICT)
    fun handleUsuarioJaCadastrado(exception: UsuarioJaCadastradoException, request: HttpServletRequest): ErrorView {
        return ErrorView(
            status = HttpStatus.CONFLICT.value(),
            error = HttpStatus.CONFLICT.name,
            message = exception.message,
            path = request.servletPath
        )
    }
}