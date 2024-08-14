package br.com.rodritodev.forum.dto

/**
 * Formulário de criação de um novo tópico no fórum
 *
 * Form é um dto que chega na aplicação
 */
data class NovoTopicoForm(
    val titulo: String,
    val mensagem: String,
    val idCurso: Long,
    val idAutor: Long,
)