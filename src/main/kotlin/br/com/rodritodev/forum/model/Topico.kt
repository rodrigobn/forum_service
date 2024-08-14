package br.com.rodritodev.forum.model

import java.time.LocalDateTime

/**
 * Modelo de tópico do fórum
 */
data class Topico(
    var id: Long? = null,
    val titulo: String,
    val mensagem: String,
    val dataCriacao: LocalDateTime = LocalDateTime.now(),
    val curso: Curso,
    val autor: Usuario,
    val status: StatusTopico = StatusTopico.NAO_RESPONDIDO,
    val resposta: List<Resposta> = ArrayList(),
)