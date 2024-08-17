package br.com.rodritodev.forum.model

import br.com.rodritodev.forum.dto.TopicoView
import java.time.LocalDateTime

/**
 * Resposta de um tópico
 */
data class Resposta(
    var id: Long? = null,
    val mensagem: String,
    val dataCriacao: LocalDateTime = LocalDateTime.now(),
    var usuario: Usuario? = null,
    var topico: TopicoView? = null,
    val solucao: Boolean,
)