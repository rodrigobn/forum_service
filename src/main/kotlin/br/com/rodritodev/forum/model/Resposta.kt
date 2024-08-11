package br.com.rodritodev.forum.model

import java.time.LocalDateTime

/**
 * Resposta de um tópico
 */
data class Resposta(
    val id: Long? = null,
    val mensagem: String,
    val dataCriacao: LocalDateTime = LocalDateTime.now(),
    val usuario: Usuario,
    val topico: Topico,
    val solucao: Boolean,
)