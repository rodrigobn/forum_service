package br.com.rodritodev.forum.service

import br.com.rodritodev.forum.dto.AtualizacaoTopicoForm
import br.com.rodritodev.forum.dto.NovoTopicoForm
import br.com.rodritodev.forum.dto.TopicoPorCategoriaDto
import br.com.rodritodev.forum.dto.TopicoView
import br.com.rodritodev.forum.exception.NotFoundException
import br.com.rodritodev.forum.mapper.TopicoFormMapper
import br.com.rodritodev.forum.mapper.TopicoViewMapper
import br.com.rodritodev.forum.repository.TopicoRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.time.LocalDateTime

/**
 * Serviço de tópicos do fórum
 */
@Service
class TopicoService(
    private var repository: TopicoRepository,
    private val topicoViewMapper: TopicoViewMapper,
    private val topicoFormMapper: TopicoFormMapper,
) {
    /**
     * Lista todos os tópicos ou filtra por nome do curso
     * @return Lista de tópicos
     */
    fun listar(
        nomeCurso: String?,
        paginacao: Pageable
    ): Page<TopicoView> {
        if (nomeCurso != null) {
            return repository.findByCursoNome(nomeCurso, paginacao).map(topicoViewMapper::map)
        }
        return repository.findAll(paginacao).map(topicoViewMapper::map)
    }

    /**
     * Busca um tópico pelo id
     * @param id Id do tópico
     * @return Tópico encontrado
     */
    fun buscarPorId(id: Long): TopicoView {
        val topico = repository.findById(id).orElseThrow {
            NotFoundException("Tópico não encontrado")
        }

        return topicoViewMapper.map(topico)
    }

    /**
     * Cadastra um novo tópico
     * @param dto Dados do tópico
     */
    fun cadastrar(dto: NovoTopicoForm): TopicoView {
        val topico = topicoFormMapper.map(dto)
        repository.save(topico)
        return topicoViewMapper.map(topico)
    }

    /**
     * Atualiza um tópico
     * @param dto Dados do tópico
     */
    fun atualizar(dto: AtualizacaoTopicoForm): TopicoView {
        val topico = repository.findById(dto.id).orElseThrow {
            NotFoundException("Tópico não encontrado")
        }
        topico.titulo = dto.titulo
        topico.mensagem = dto.mensagem
        topico.dataAlteracao = LocalDateTime.now()
        repository.save(topico)

        return topicoViewMapper.map(topico)
    }

    /**
     * Deleta um tópico
     * @param id Id do tópico
     */
    fun deletar(id: Long) {
        repository.findById(id).orElseThrow {
            NotFoundException("Tópico não encontrado")
        }
        repository.deleteById(id)
    }

    /**
     * Relatório de tópicos por categoria
     * @return Lista de tópicos por categoria
     */
    fun relatorio(): List<TopicoPorCategoriaDto> {
        return repository.relatorio()
    }
}