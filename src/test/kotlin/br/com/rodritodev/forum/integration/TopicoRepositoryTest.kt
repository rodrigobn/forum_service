package br.com.rodritodev.forum.integration

import br.com.rodritodev.forum.dto.TopicoPorCategoriaDto
import br.com.rodritodev.forum.model.TopicoTest
import br.com.rodritodev.forum.repository.TopicoRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@SpringBootTest
@Testcontainers // Essa anotação é usada para testes que utilizam containers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // Não substitui o banco de dados
class TopicoRepositoryTest {

    // Injeta o repositório de tópicos
    @Autowired
    private lateinit var topicoRepository: TopicoRepository

    private val topico = TopicoTest.build()

    companion object {
        @Container // Anotação para criar um container de teste
        private val container = MySQLContainer<Nothing>("mysql:8.3.0").apply {
            withDatabaseName("testedb")
            withUsername("rodrigo")
            withPassword("123456")
        }

        @JvmStatic
        @DynamicPropertySource // Anotação para configurar propriedades dinâmicas no contexto do Spring
        fun properties(registry: DynamicPropertyRegistry) {
            // Configuração do datasource
            registry.add("spring.datasource.url", container::getJdbcUrl)
            registry.add("spring.datasource.username", container::getUsername)
            registry.add("spring.datasource.password", container::getPassword)
        }

        @JvmStatic
        @BeforeAll
        fun setUp() {
            container.start() // Certifica que o container está ativo antes dos testes
            assertThat(container.isRunning).isTrue() // Garante que o container está rodando
        }
    }

    @Test
    fun `deve retornar um relatorio de topicos por categoria`() {
        topicoRepository.save(topico)
        val relatorio = topicoRepository.relatorio()

        assertThat(relatorio).isNotNull
        assertThat(relatorio.first()).isExactlyInstanceOf(TopicoPorCategoriaDto::class.java)
    }

    @Test
    fun `deve listar topicos por nome do curso`() {
        topicoRepository.save(topico)
        val topicos = topicoRepository.findByCursoNome(topico.curso.nome, PageRequest.of(0, 5))

        assertThat(topicos).isNotNull
    }
}