package br.com.rodritodev.forum.security

import br.com.rodritodev.forum.config.JWTUtil
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

/**
 * Filtro de autenticação JWT.
 * Esse filtro é responsável por interceptar as requisições e validar o token JWT presente no cabeçalho da requisição.
 *
 * Classe responsavel por tentar autenticar e liberar o acesso do usuário.
 *
 * @property jwtUtil Classe utilitária para manipulação de tokens JWT. *
 */
class JWTAuthenticationFilter(private val jwtUtil: JWTUtil) : OncePerRequestFilter() {

    /**
     * Método responsável por tentar autenticar e liberar o acesso do usuário.
     *
     * @param request Requisição HTTP.
     * @param response Resposta HTTP.
     * @param filterChain Filtro de requisição.
     */
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authorizationHeader = request.getHeader("Authorization")

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            val token = authorizationHeader.substring(7) // Pega a string a partir da posição 7 para remover "Bearer "
            // Agora você pode usar o token para validar
            if (jwtUtil.isTokenValid(token)) {
                // Continua a lógica de validação ou autenticação
                val authentication = jwtUtil.getAuthentication(token)
                SecurityContextHolder.getContext().authentication = authentication
            }
            // Continua o processamento da requisição
            filterChain.doFilter(request, response)
        }
    }
}
