package br.com.rodritodev.forum.model

object TopicoTest {
    fun build() = Topico(
        id = 1,
        titulo = "Título",
        mensagem = "Mensagem",
        curso = Curso(
            id = 1,
            nome = "Curso",
            categoria = "Categoria",
        ),
        autor = Usuario(
            id = 1,
            nome = "Autor",
            email = "autor@mail.com",
            senha = "123456",
            roles = listOf(),
        ),
        status = StatusTopico.NAO_RESPONDIDO,
        resposta = listOf(),
        dataAlteracao = null,
    )
}