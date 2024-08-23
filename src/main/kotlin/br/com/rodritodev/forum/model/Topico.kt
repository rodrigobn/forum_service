package br.com.rodritodev.forum.model

import jakarta.persistence.*
import java.time.LocalDateTime

/**
 * Modelo de tópico do fórum
 */
@Entity
data class Topico(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    var titulo: String = "",
    var mensagem: String = "",
    val dataCriacao: LocalDateTime = LocalDateTime.now(),
    @ManyToOne
    val curso: Curso = Curso(),
    @ManyToOne
    val autor: Usuario = Usuario(),
    @Enumerated(value = EnumType.STRING)
    val status: StatusTopico = StatusTopico.NAO_RESPONDIDO,
    @OneToMany(mappedBy = "topico")
    var resposta: List<Resposta> = ArrayList(),
)