package br.com.rodritodev.forum.dto

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

/**
 * Formulário de criação de um novo tópico no fórum
 *
 * Form é um dto que chega na aplicação
 * @property titulo Título do tópico
 * @property mensagem Mensagem do tópico
 * @property idCurso Id do curso do tópico
 * @property idAutor Id do autor do tópico
 */
data class NovoTopicoForm(
    @field:NotEmpty(message = "O título não pode ser em branco")
    @field:Size(min = 5, max = 50, message = "O título deve ter entre 5 e 50 caracteres")
    val titulo: String,

    @field:NotEmpty(message = "A mensagem não pode ser em branco")
    @field:Size(min = 5, max = 150, message = "A mensagem deve ter entre 5 e 150 caracteres")
    val mensagem: String,

    @field:NotNull val
    idCurso: Long,

    @field:NotNull val
    idAutor: Long,
)