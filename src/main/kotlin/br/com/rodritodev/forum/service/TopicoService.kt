package br.com.rodritodev.forum.service

import br.com.rodritodev.forum.dto.AtualizacaoTopicoForm
import br.com.rodritodev.forum.dto.NovoTopicoForm
import br.com.rodritodev.forum.dto.TopicoView
import br.com.rodritodev.forum.mapper.TopicoFormMapper
import br.com.rodritodev.forum.mapper.TopicoViewMapper
import br.com.rodritodev.forum.model.Topico
import org.springframework.stereotype.Service

/**
 * Serviço de tópicos do fórum
 */
@Service
class TopicoService(
    private var topicos: List<Topico> = ArrayList(),
    private val topicoViewMapper: TopicoViewMapper,
    private val topicoFormMapper: TopicoFormMapper,
) {
    /**
     * Retorna uma lista de tópicos
     * @return Lista de tópicos
     */
    fun listar(): List<TopicoView> {
        return topicos.stream().map { topico ->
            topicoViewMapper.map(topico)
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
            return topicoViewMapper.map(topico)
        }
        throw IllegalArgumentException("Tópico ($id) não encontrado")
    }

    /**
     * Cadastra um novo tópico
     * @param dto Dados do tópico
     */
    fun cadastrar(dto: NovoTopicoForm): TopicoView {
        val topico = topicoFormMapper.map(dto)
        topico.id = topicos.size.toLong() + 1
        topicos = topicos.plus(topico)
        return topicoViewMapper.map(topico)
    }

    /**
     * Atualiza um tópico
     * @param dto Dados do tópico
     */
    fun atualizar(dto: AtualizacaoTopicoForm): TopicoView{
        val topico = topicos.find { it.id == dto.id }

        if (topico == null) {
            throw IllegalArgumentException("Tópico (${dto.id}) não encontrado")
        }

        val topicoAtualizado = Topico(
            id = dto.id,
            titulo = dto.titulo,
            mensagem = dto.mensagem,
            dataCriacao = topico.dataCriacao,
            curso = topico.curso,
            autor = topico.autor,
            status = topico.status,
            resposta = dto.respostas,
        )

        topicos = topicos.minus(topico).plus(topicoAtualizado)

        return topicoViewMapper.map(topicoAtualizado)
    }

    /**
     * Deleta um tópico
     * @param id Id do tópico
     */
    fun deletar(id: Long) {
        val topico = topicos.find { it.id == id }

        if (topico == null) {
            throw IllegalArgumentException("Tópico ($id) não encontrado")
        }

        topicos = topicos.minus(topico)
    }
}