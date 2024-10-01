package br.com.rodritodev.forum.config

import br.com.rodritodev.forum.model.Roles
import br.com.rodritodev.forum.security.JWTAuthenticationFilter
import br.com.rodritodev.forum.security.JWTLoginFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.filter.OncePerRequestFilter


private const val s = "ROLE_USER"

@Configuration
@EnableWebSecurity
class SecurityConfiguration(
    private val userDetailService: UserDetailsService,
    private val jwtUtil: JWTUtil
) {
    /**
     * Configura o filtro de segurança da aplicação
     *
     * @param http Configuração de segurança
     * @param authConfig Configuração de autenticação
     * @return Retorna o filtro de segurança da aplicação
     */
    @Bean
    fun securityFilterChain(http: HttpSecurity, authConfig: AuthenticationConfiguration): SecurityFilterChain {
        http
            .csrf { csrf -> csrf.disable() } // Desabilita o CSRF (Cross-Site Request Forgery) para APIs REST
            .headers { headers -> headers.frameOptions { it.sameOrigin() } } // Permite o uso do console do H2 no mesmo domínio da aplicação
            .addFilterBefore(JWTLoginFilter(authManager = authenticationManager(authConfig), jwtUtil = jwtUtil), UsernamePasswordAuthenticationFilter().javaClass) // Adiciona os filtros de autenticação e autorização
            .addFilterBefore(JWTAuthenticationFilter(jwtUtil = jwtUtil), UsernamePasswordAuthenticationFilter().javaClass) // Adiciona o filtro de autenticação JWT antes de qualquer outro filtro de requisição
            .authorizeHttpRequests { authz ->
                // Configura as autorizações de acesso aos endpoints da aplicação
                authz.requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/h2-console/**", "/webjars/**").permitAll() // Permite acesso sem autenticação ao Swagger e H2 Console
                authz.requestMatchers(HttpMethod.POST, "/login").permitAll() // Permite acesso ao endpoint de login sem autenticação
                authz.requestMatchers(HttpMethod.POST, "/usuarios/**").hasAuthority(Roles.ROLE_ADMIN.name) // Apenas ADMIN pode cadastrar usuários
                authz.requestMatchers(HttpMethod.DELETE, "/usuarios/**").hasAuthority(Roles.ROLE_ADMIN.name) // Apenas ADMIN pode excluir usuários
                authz.requestMatchers("/topicos/**").hasAuthority(Roles.ROLE_USER.name) // Apenas USUÁRIO pode acessar os tópicos
                authz.anyRequest().authenticated() // Qualquer outra requisição precisa de autenticação
            }
            .sessionManagement { session ->
                // Desabilita a criação de sessão no servidor
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
        return http.build()
    }

    /**
     * Configura o `AuthenticationManager` que gerencia a autenticação
     *
     * @param authConfig Configuração de autenticação
     * @return Retorna o gerenciador de autenticação
     * @throws Exception
     */
    @Bean
    @Throws(Exception::class)
    fun authenticationManager(
        authConfig: AuthenticationConfiguration
    ): AuthenticationManager {
        return authConfig.authenticationManager
    }

    /**
     * Configura o `AuthenticationManagerBuilder` para gerenciar a autenticação
     *
     * @param auth Gerenciador de autenticação
     * @throws Exception
     */
    fun configureGlobal(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder())
    }

    /**
     * Configura o `BCryptPasswordEncoder` para criptografar a senha do usuário
     *
     * @return Retorna o codificador de senha
     */
    @Bean
    fun passwordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }
}