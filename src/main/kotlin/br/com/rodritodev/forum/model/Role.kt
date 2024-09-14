package br.com.rodritodev.forum.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.springframework.security.core.GrantedAuthority

/**
 * Modelo de Role
 *
 * @property id Identificador da role
 * @property nome Nome da role
 * @constructor Cria uma role
 * @see GrantedAuthority
 */
@Entity
data class Role(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long,
    private val nome: String
) : GrantedAuthority {

    /**
     * Retorna a autoridade do usuário (ROLE_USER, ROLE_ADMIN, etc)
     * @return Autoridade
     */
    override fun getAuthority() = nome

}