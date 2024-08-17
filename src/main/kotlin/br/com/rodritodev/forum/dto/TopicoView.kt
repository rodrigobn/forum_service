package br.com.rodritodev.forum.dto

import br.com.rodritodev.forum.model.Resposta
import br.com.rodritodev.forum.model.StatusTopico
import java.time.LocalDateTime

/**
 * Visualização de um tópico do fórum
 *
 * View é um dto que sai da aplicação
 */
data class TopicoView(
    val id: Long?,
    val titulo: String,
    val mensagem: String,
    val status: StatusTopico,
    val dataCriacao: LocalDateTime,
    var respostas: List<RespostaView>,
)
