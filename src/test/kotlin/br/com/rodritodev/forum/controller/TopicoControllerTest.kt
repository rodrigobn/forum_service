package br.com.rodritodev.forum.controller

import br.com.rodritodev.forum.config.JWTUtil
import br.com.rodritodev.forum.model.Role
import br.com.rodritodev.forum.model.UsuarioTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TopicoControllerTest {

    // WebApplicationContext é uma interface que representa o contexto da aplicação web e é usada para testar controllers de forma isolada e sem subir o servidor
    @Autowired
    private lateinit var webApplicationContext: WebApplicationContext

    // JWTUtil é uma classe que gera tokens JWT para autenticação de usuários na aplicação
    @Autowired
    private lateinit var jwtUtil: JWTUtil

    // MockMvc é uma classe que simula requisições HTTP para testar controllers de forma isolada e sem subir o servidor
    private lateinit var mockMvc: MockMvc

    private var jwtToken: String? = null

    companion object {
        private const val TOKEN = "%s"
        private const val URI = "/topicos/"
        private const val URI_WITH_PARAM = URI.plus("%s")
    }

    // Método que é executado antes de cada teste
    @BeforeEach
    fun setUp() {
        // Gera um token JWT para ser utilizado nos testes
        jwtToken = generateToken()

        // Configura o MockMvc para testar a aplicação
        mockMvc = MockMvcBuilders
            .webAppContextSetup(webApplicationContext)
            .apply<DefaultMockMvcBuilder?>(SecurityMockMvcConfigurers.springSecurity())
            .build()
    }

    // Testes
    @Test
    fun `deve retornar codigo 400 quando chamar topicos sem autenticacao`() {
        mockMvc.get(URI).andExpect { status { is4xxClientError() } }
    }

    // TODO - Corrigir o teste para verificar se o status retornado é 200 ao buscar todos os tópicos com um token
    @Test
    fun `deve retornar status 200 ao buscar todos os topicos com um token`() {
        mockMvc.get(URI) {
            headers { this.setBearerAuth(TOKEN.format(jwtToken)) }
        }.andExpect { status { is2xxSuccessful() } }
    }

    // TODO - Corrigir o teste para verificar se o status retornado é 200 ao buscar um tópico por id com um token
    @Test
    fun `deve retornar codigo 200 ao buscar um topico por id com um token`() {
        mockMvc.get(URI_WITH_PARAM.format(1)) {
            headers { this.setBearerAuth(TOKEN.format(jwtToken)) }
        }.andExpect { status { is2xxSuccessful() } }
    }

    private fun generateToken(): String {
        val authorities = mutableListOf(Role(1, "ROLE_ADMIN"))
        val usuario = UsuarioTest.buildToToken()

        return jwtUtil.generateToken(usuario.email, authorities)
    }
}