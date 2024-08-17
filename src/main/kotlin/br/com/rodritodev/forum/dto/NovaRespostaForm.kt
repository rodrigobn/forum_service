package br.com.rodritodev.forum.dto

/**
 * Formulário de nova resposta
 *
 * Form é um dto que entra na aplicação
 */
data class NovaRespostaForm(
    val mensagem: String,
    val idTopico: Long,
    val idAutor: Long,
)