package br.com.rodritodev.forum.dto

import br.com.rodritodev.forum.model.StatusTopico
import java.time.LocalDateTime

/**
 * Visualização de um tópico do fórum
 */
data class TopicoView(
    val id: Long?,
    val titulo: String,
    val mensagem: String,
    val status: StatusTopico,
    val dataCriacao: LocalDateTime,
)
