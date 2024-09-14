package br.com.rodritodev.forum.service

import br.com.rodritodev.forum.model.Usuario
import org.springframework.security.core.userdetails.UserDetails

class UserDetail(
    private val usuario: Usuario,
): UserDetails {

    /**
     * Retorna as autoridades do usuário
     * @return Lista de autoridades
     */
    override fun getAuthorities() = usuario.roles

    /**
     * Retorna a senha do usuário
     * @return Senha do usuário
     */
    override fun getPassword() = usuario.senha

    /**
     * Retorna o nome de usuário
     * @return Nome de usuário
     */
    override fun getUsername() = usuario.email

    /**
     * Verifica se a conta do usuário está expirada
     * @return Verdadeiro se não estiver expirada, falso caso contrário
     */
    override fun isAccountNonExpired() = true

    /**
     * Verifica se a conta do usuário está bloqueada
     * @return Verdadeiro se não estiver bloqueada, falso caso contrário
     */
    override fun isAccountNonLocked() = true

    /**
     * Verifica se as credenciais do usuário estão expiradas
     * @return Verdadeiro se não estiverem expiradas, falso caso contrário
     */
    override fun isCredentialsNonExpired() = true

    /**
     * Verifica se o usuário está habilitado
     * @return Verdadeiro se estiver habilitado, falso caso contrário
     */
    override fun isEnabled() = true
}