package br.com.rodritodev.forum.repository

import br.com.rodritodev.forum.model.Usuario
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

/**
 * Repositório de usuários
 */
interface UsuarioRepository: JpaRepository<Usuario, Long>{
    /**
     * Verifica se existe um usuário com o nome
     * @param nome Nome do usuário
     * @return Verdadeiro se existir, falso caso contrário
     */
    fun existsByNome(nome: String): Boolean

    /**
     * Verifica se existe um usuário com o email
     * @param email Email do usuário
     * @return Verdadeiro se existir, falso caso contrário
     */
    fun existsByEmail(email: String): Boolean

    /**
     * Busca usuário por email
     * @param email Email do usuário
     * @return Usuário encontrado
     */
    fun findByEmail(email: String): Usuario?

    /**
     * Busca usuário por nome
     * @param nome Nome do usuário
     * @return Usuário encontrado
     */
    fun findByNome(nome: String): Usuario?

    /**
     * Busca usuário por nome e email
     * @param nome Nome do usuário
     * @param email Email do usuário
     * @return Usuário encontrado
     */
    fun findByNomeAndEmail(nome: String, email: String): Usuario?

    /**
     * Busca usuário por nome ou email
     * @param nome Nome do usuário
     * @param email Email do usuário
     * @return Usuário encontrado
     */
    fun findByNomeOrEmail(nome: String, email: String): Usuario?

    /**
     * Busca usuário por nome contendo
     * @param nome Nome do usuário
     * @return Lista de usuários encontrados
     */
    fun findByNomeContaining(nome: String, paginacao: Pageable): Page<Usuario>

    /**
     * Busca usuário por email contendo
     * @param email Email do usuário
     * @return Lista de usuários encontrados
     */
    fun findByEmailContaining(email: String, paginacao: Pageable): Page<Usuario>

    /**
     * Busca usuário por nome e email contendo
     * @param nome Nome do usuário
     * @param email Email do usuário
     * @return Lista de usuários encontrados
     */
    fun findByNomeContainingAndEmailContaining(nome: String, email: String, paginacao: Pageable): Page<Usuario>
}