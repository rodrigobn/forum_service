package br.com.rodritodev.forum.repository

import br.com.rodritodev.forum.dto.TopicoPorCategoria
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

    @Query("SELECT new br.com.rodritodev.forum.dto.TopicoPorCategoria(curso.nome, count(t)) FROM Topico t JOIN t.curso curso GROUP BY curso.categoria")
    fun relatorio(): List<TopicoPorCategoria>

    @Query("select t from Topico t where t.respostas is empty")
    fun topicosNaoRespondidos(): List<Topico>
}