package br.com.rodritodev.forum.config

import br.com.rodritodev.forum.service.UsuarioService
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.SecretKey

/**
 * Classe responsável por gerar o token JWT para o usuário. O token é gerado com base no id do usuário e tem duração de 30 minutos.
 *
 * @property usuario Usuário que será utilizado para gerar o token.
 * @return Token JWT gerado.
 *
 */
@Component
class JWTUtil(
    private val usuarioService: UsuarioService
) {
    @Value("\${security.jwt.expiration}")
    private lateinit var expirationTime: String // Tempo de expiração do token (24 horas)

    private val secretKey: SecretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512) // Chave secreta para assinar o token


    /**
     * Gera um token JWT para o usuário.
     *
     * @param username Nome do usuário que será utilizado para gerar o token.
     * @return Token JWT gerado.
     *
     */
    fun generateToken(username: String, authorities: MutableCollection<out GrantedAuthority>): String {
        val expirationInMillis = System.currentTimeMillis() + expirationTime.toLong()

        return Jwts.builder()
            .setSubject(username) // Usuário do token
            .claim("role", authorities) // Autorizações do usuário
            .setIssuedAt(Date(System.currentTimeMillis())) // Data de criação do token
            .claim("expMillis", Date(expirationInMillis)) // Expiração em data e hora
            .setExpiration(Date(expirationInMillis)) // Salvando a data de expiração do token em segundos conforme o padrão RFC 7519 (Unix Epoch)
            .signWith(SignatureAlgorithm.HS512, secretKey) // Assinatura do token com a chave secreta
            .compact()
    }

    /**
     * Verifica se o token JWT é válido.
     *
     * @param jwt Token JWT a ser validado.
     * @return true se o token é válido, false caso contrário.
     *
     */
    fun isTokenValid(jwt: String?): Boolean {
        return try {
            // Parsing do JWT com a chave secreta para validar a assinatura
            Jwts.parser()
                .setSigningKey(secretKey) // Definindo a chave de assinatura
                .parseClaimsJws(jwt) // Parsing do JWT
            true
        } catch (e: IllegalArgumentException) {
            // Qualquer exceção durante o parsing indica que o token não é válido
            false
        }
    }

    fun getAuthentication(jwt: String?): Authentication {
        val username = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwt).body.subject
        val user = usuarioService.loadUserByUsername(username)
        return UsernamePasswordAuthenticationToken(username, null, user.authorities)
    }
}