package br.com.rodritodev.forum.security

import br.com.rodritodev.forum.config.JWTUtil
import br.com.rodritodev.forum.model.Credentials
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

/**
 * Filtro de autenticação JWT.
 * Esse filtro é responsável por interceptar as requisições e validar o token JWT presente no cabeçalho da requisição.
 *
 * Classe responsavel por tentar autenticar o usuário e gerar o token JWT.
 *
 * @property jwtUtil Classe utilitária para manipulação de tokens JWT. *
 */
class JWTLoginFilter(private val authManager: AuthenticationManager, private val jwtUtil: JWTUtil) :
    UsernamePasswordAuthenticationFilter() {

    /**
     * Método responsável por tentar autenticar o usuário.
     *
     * @param request Requisição HTTP.
     * @param response Resposta HTTP.
     * @return Objeto de autenticação.
     */
    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
        val credentials = ObjectMapper().readValue(request.inputStream, Credentials::class.java)
        val token = UsernamePasswordAuthenticationToken(credentials.username, credentials.senha)
        return authManager.authenticate(token)
    }

    /**
     * Método responsável por gerar o token JWT e adicionar no cabeçalho da resposta.
     *
     * @param request Requisição HTTP.
     * @param response Resposta HTTP.
     * @param chain Filtro de requisição.
     * @param authResult Objeto de autenticação.
     */
    override fun successfulAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain,
        authResult: Authentication
    ) {
        val user = (authResult.principal as UserDetails)
        val token = jwtUtil.generateToken(user.username, user.authorities)
        response.addHeader("Authorization", "Bearer $token")
        response.addHeader("access-control-expose-headers", "Authorization")
        response.addHeader("Content-Type", "application/json")
    }

}
