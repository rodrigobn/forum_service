package br.com.rodritodev.forum.service

import br.com.rodritodev.forum.dto.NovoTopicoDto
import br.com.rodritodev.forum.model.*
import org.springframework.stereotype.Service

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
    fun listar(): List<Topico> {
        return topicos
    }

    /**
     * Busca um tópico pelo id
     * @param id Id do tópico
     * @return Tópico encontrado
     */
    fun buscarPorId(id: Long): Topico {
        topicos.forEach {
            if (it.id == id) {
                return it
            }
        }
        throw IllegalArgumentException("Tópico ($id) não encontrado")
    }

    /**
     * Cadastra um novo tópico
     * @param dto Dados do tópico
     */
    fun cadastrar(dto: NovoTopicoDto) {
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