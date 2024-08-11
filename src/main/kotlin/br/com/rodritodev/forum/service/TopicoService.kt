package br.com.rodritodev.forum.service

import br.com.rodritodev.forum.dto.NovoTopicoForm
import br.com.rodritodev.forum.dto.TopicoView
import br.com.rodritodev.forum.model.*
import org.springframework.stereotype.Service

/**
 * Serviço de tópicos do fórum
 */
@Service
class TopicoService(
    private var topicos: List<Topico> = ArrayList(),
    private val cursoService: CursoService,
    private val usuarioService: UsuarioService,
) {

    /**
     * Retorna uma lista de tópicos
     * @return Lista de tópicos
     */
    fun listar(): List<TopicoView> {
        return topicos.stream().map { topico ->
            TopicoView(
                id = topico.id,
                titulo = topico.titulo,
                mensagem = topico.mensagem,
                status = topico.status,
                dataCriacao = topico.dataCriacao,
            )
        }.toList()
    }

    /**
     * Busca um tópico pelo id
     * @param id Id do tópico
     * @return Tópico encontrado
     */
    fun buscarPorId(id: Long): TopicoView {
        val topico = topicos.find { it.id == id }
        if (topico != null) {
            return TopicoView(
                id = topico.id,
                titulo = topico.titulo,
                mensagem = topico.mensagem,
                status = topico.status,
                dataCriacao = topico.dataCriacao,
            )
        }
        throw IllegalArgumentException("Tópico ($id) não encontrado")
    }

    /**
     * Cadastra um novo tópico
     * @param dto Dados do tópico
     */
    fun cadastrar(dto: NovoTopicoForm) {
        topicos = topicos.plus(
            Topico(
                id = topicos.size.toLong() + 1,
                titulo = dto.titulo,
                mensagem = dto.mensagem,
                curso = cursoService.buscarPorId(dto.idCurso),
                autor = usuarioService.buscarPorId(dto.idAutor),
            )
        )
    }
}