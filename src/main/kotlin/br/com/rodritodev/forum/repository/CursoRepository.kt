package br.com.rodritodev.forum.repository

import br.com.rodritodev.forum.model.Curso
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

/**
 * Repositório de cursos
 */
interface CursoRepository: JpaRepository<Curso, Long> {

    /**
     * Busca curso por nome
     * @param nome Nome do curso
     * @return Curso encontrado
     */
    fun findByNome(nome: String): Curso

    /**
     * Busca curso por nome do curso contendo
     * @param nomeCurso Nome do curso
     * @return Lista de cursos encontrados
     */
    fun findByNomeContaining(nomeCurso: String, paginacao: Pageable): Page<Curso>
}