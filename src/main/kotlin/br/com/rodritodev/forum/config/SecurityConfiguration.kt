package br.com.rodritodev.forum.config

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


private const val s = "ROLE_USER"

@Configuration
@EnableWebSecurity
class SecurityConfiguration(
    private val userDetailService: UserDetailsService
) {
    /**
     * Essa é a nova abordagem para configurar a segurança sem a necessidade de estender WebSecurityConfigurerAdapter.
     * Declaramos a segurança diretamente no bean.
     *
     * @param http Configuração de segurança HTTP
     *
     * @return Retorna a configuração de segurança
     */
    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.csrf { csrf -> csrf.disable() } // Desabilita o CSRF (Cross-Site Request Forgery) para APIs REST
            .headers { headers -> headers.frameOptions { it.sameOrigin() } } // Permite o uso do console do H2 no mesmo domínio da aplicação
            .authorizeHttpRequests { authz ->
                // Configura as autorizações de acesso aos endpoints da aplicação
                authz.requestMatchers(HttpMethod.POST, "/usuarios")
                    .hasAuthority("ROLE_ADMIN") // Apenas ADMIN pode cadastrar usuários
                authz.requestMatchers("/h2-console/**").permitAll() // Permite acesso ao console do H2 sem autenticação
                authz.anyRequest().authenticated() // Qualquer outra requisição precisa de autenticação
            }
            .sessionManagement { session ->
                // Desabilita a criação de sessão no servidor
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .httpBasic(Customizer.withDefaults()) // Usar autenticação HTTP Basic
            .formLogin {
                it.disable() // Desabilita o formulário de login (não necessário para APIs).
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
     * Configura a autenticação, incluindo o serviço de usuários e o encoder de senha
     */
    fun configureGlobal(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetailService).passwordEncoder(BCryptPasswordEncoder())
    }

    @Bean
    fun passwordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }
}