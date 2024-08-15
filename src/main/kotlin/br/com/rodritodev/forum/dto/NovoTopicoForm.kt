package br.com.rodritodev.forum.dto

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

/**
 * Formulário de criação de um novo tópico no fórum
 *
 * Form é um dto que chega na aplicação
 */
data class NovoTopicoForm(
    @field:NotEmpty @Size(min = 5, max = 50) val titulo: String,
    @field:NotEmpty @Size(min = 5, max = 150) val mensagem: String,
    @field:NotNull val idCurso: Long,
    @field:NotNull val idAutor: Long,
)