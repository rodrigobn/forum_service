package br.com.rodritodev.forum.dto

import java.time.LocalDateTime

/**
 * Visualização de uma resposta do fórum
 *
 * View é um dto que sai da aplicação
 */
data class RespostaView(
    val id: Long?,
    val mensagem: String,
    val dataCriacao: LocalDateTime,
    val nomeAutor: String,
    val solucao: Boolean,
)
