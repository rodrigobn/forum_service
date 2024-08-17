package br.com.rodritodev.forum.dto

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull

/**
 * Formulário de nova resposta
 *
 * Form é um dto que entra na aplicação
 */
data class NovaRespostaForm(
    @field:NotEmpty val mensagem: String,
    @field:NotNull val idTopico: Long,
    @field:NotNull val idAutor: Long,
)