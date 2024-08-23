package br.com.rodritodev.forum.model

import br.com.rodritodev.forum.dto.TopicoView
import br.com.rodritodev.forum.dto.UsuarioView
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import java.time.LocalDateTime

/**
 * Resposta de um tópico
 */
@Entity
data class Resposta(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    var mensagem: String = "",
    val dataCriacao: LocalDateTime = LocalDateTime.now(),
    @ManyToOne
    var usuario: Usuario? = Usuario(),
    @ManyToOne
    var topico: Topico? = Topico(),
    var solucao: Boolean = false,
)