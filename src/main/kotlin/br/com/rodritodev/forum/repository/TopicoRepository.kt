package br.com.rodritodev.forum.repository

import br.com.rodritodev.forum.dto.TopicoPorCategoriaDto
import br.com.rodritodev.forum.model.Topico
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

/**
 * Repositório de tópicos
 */
interface TopicoRepository: JpaRepository<Topico, Long>{

    /**
     * Busca tópicos por nome do curso
     * @param nomeCurso Nome do curso
     * @return Lista de tópicos encontrados
     */
    fun findByCursoNome(nomeCurso: String, paginacao: Pageable): Page<Topico>

    /**
     * Busca tópicos por título do tópico contendo
     * @param titulo Título do tópico
     * @return Lista de tópicos encontrados
     */
    fun findByTituloContaining(titulo: String, paginacao: Pageable): Page<Topico>

    /**
     * Busca tópicos por mensagem do tópico contendo
     * @param mensagem Mensagem do tópico
     * @return Lista de tópicos encontrados
     */
    fun findByMensagemContaining(mensagem: String, paginacao: Pageable): Page<Topico>

    @Query("SELECT new br.com.rodritodev.forum.dto.TopicoPorCategoriaDto(curso.categoria, count(t)) FROM Topico t JOIN t.curso curso GROUP BY curso.categoria")
    fun relatorio(): List<TopicoPorCategoriaDto>

    /**
     * Busca tópicos sem respostas
     * @return Lista de tópicos encontrados
     */
//    @Query("SELECT t FROM Topico t WHERE t.respostas IS EMPTY")
//    fun topicosNaoRespondidos(): List<Topico>
}