package br.com.rodritodev.forum.model

import br.com.rodritodev.forum.dto.TopicoView
import java.time.LocalDateTime

object TopicoViewTest {
    fun build() = TopicoView(
        id = 1,
        titulo = "Título",
        mensagem = "Mensagem",
        status = StatusTopico.NAO_RESPONDIDO,
        dataCriacao = LocalDateTime.now(),
        respostas = listOf(),
        dataAlteracao = LocalDateTime.now(),
    )
}