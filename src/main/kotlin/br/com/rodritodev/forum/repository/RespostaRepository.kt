package br.com.rodritodev.forum.repository

import br.com.rodritodev.forum.model.Resposta
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

/**
 * Repositório de respostas
 */
interface RespostaRepository: JpaRepository<Resposta, Long> {

    /**
     * Busca respostas por mensagem da resposta contendo
     * @param mensagem Mensagem da resposta
     * @return Lista de respostas encontradas
     */
    fun findByMensagemContaining(mensagem: String, paginacao: Pageable): Page<Resposta>

    /**
     * Busca respostas por tópico
     * @param topicoId Id do tópico
     * @return Lista de respostas encontradas
     */
    fun findByTopicoId(topicoId: Long, paginacao: Pageable): Page<Resposta>

    /**
     * Busca respostas por tópico e mensagem da resposta contendo
     * @param topicoId Id do tópico
     * @param mensagem Mensagem da resposta
     * @return Lista de respostas encontradas
     */
    fun findByTopicoIdAndMensagemContaining(topicoId: Long, mensagem: String, paginacao: Pageable): Page<Resposta>
}