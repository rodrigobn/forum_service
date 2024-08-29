package br.com.rodritodev.forum.model

import br.com.rodritodev.forum.dto.TopicoView
import br.com.rodritodev.forum.dto.UsuarioView
import jakarta.persistence.*
import java.time.LocalDateTime

/**
 * Resposta de um tópico
 */
@Entity
data class Resposta(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var mensagem: String,
    val dataCriacao: LocalDateTime = LocalDateTime.now(),
    @ManyToOne
    @JoinColumn(name = "autor_id")
    var usuario: Usuario? = null,
    @ManyToOne
    @JoinColumn(name = "topico_id")
    var topico: Topico? = null,
    var solucao: Boolean = false,
)