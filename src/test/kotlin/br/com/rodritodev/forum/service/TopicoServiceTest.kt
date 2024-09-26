package br.com.rodritodev.forum.service

import br.com.rodritodev.forum.exception.NotFoundException
import br.com.rodritodev.forum.mapper.TopicoFormMapper
import br.com.rodritodev.forum.mapper.TopicoViewMapper
import br.com.rodritodev.forum.model.Topico
import br.com.rodritodev.forum.model.TopicoTest
import br.com.rodritodev.forum.model.TopicoViewTest
import br.com.rodritodev.forum.repository.TopicoRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.assertThrows
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import java.util.*
import kotlin.test.Test
import kotlin.test.assertFailsWith

/**
 * Testes para o serviço de tópicos do fórum
 */
class TopicoServiceTest {

    private val topico = PageImpl(listOf(TopicoTest.build()))
    private val paginacao: Pageable = mockk()

    private val topicoRepository: TopicoRepository = mockk {
        // Sempre que o método findByCursoNome for chamado com qualquer argumento, retorne topico
        every { findByCursoNome(any(), any()) } returns topico
        // Sempre que o método findAll for chamado com qualquer argumento, retorne topico
        every { findAll(paginacao) } returns topico
    }

    private val topicoViewMapper: TopicoViewMapper = mockk {
        // Sempre que o método map for chamado com qualquer argumento, retorne TopicoViewTest.build()
        every { map(any()) } returns TopicoViewTest.build()
    }
    private val topicoFormMapper: TopicoFormMapper = mockk()

    private val topicoService = TopicoService(
        topicoRepository, topicoViewMapper, topicoFormMapper
    )

    @Test
    fun `deve retornar uma lista de topicos com nome do curso`() {

        // Cenário
        val nomeCurso = "Curso"
        val slotTopico = slot<Topico>() // Slot para capturar o argumento passado para o método map

        // Configuração. Pode ser feita aqui dentro do método ou no setup da classe
        every {
            topicoViewMapper.map(any())
        } returns TopicoViewTest.build()

        // Sempre que o método map for chamado com qualquer argumento, retorne TopicoViewTest.build()
        every { topicoViewMapper.map(capture(slotTopico)) } returns TopicoViewTest.build()

        // Ação
        topicoService.listar(nomeCurso, paginacao)

        // Validação
        // Verifica se o método findByCursoNome foi chamado exatamente 1 vez
        verify(exactly = 1) { topicoRepository.findByCursoNome(any(), any()) }
        verify(exactly = 1) { topicoViewMapper.map(any()) }
        // Verifica se o método findAll não foi chamado
        verify(exactly = 0) { topicoRepository.findAll(paginacao) }
        // Verifica se o método findByCursoNome foi chamado com o argumento "Curso" e paginacao
        verify { topicoRepository.findByCursoNome("Curso", paginacao) }

        // Validação do argumento passado para o método map do topicoViewMapper
        val topico = TopicoTest.build()
        assertThat(slotTopico.captured.titulo).isEqualTo(topico.titulo)
        assertThat(slotTopico.captured.mensagem).isEqualTo(topico.mensagem)
        assertThat(slotTopico.captured.status).isEqualTo(topico.status)
    }

    @Test
    fun `deve retornar uma lista de topicos sem nome do curso`() {
        // Ação
        topicoService.listar(null, paginacao)

        // Validação
        // Verifica se o método findAll foi chamado exatamente 1 vez
        verify(exactly = 1) { topicoRepository.findAll(paginacao) }
        // Verifica se o método map foi chamado exatamente 1 vez
        verify(exactly = 1) { topicoViewMapper.map(any()) }
        // Verifica se o método findByCursoNome não foi chamado
        verify(exactly = 0) { topicoRepository.findByCursoNome(any(), any()) }
    }

    @Test
    fun `deve retornar not found ao buscar por id inexistente`() {

        // Configuração
        every { topicoRepository.findById(any()) } returns Optional.empty()

        // Cenário
        val id = 1L
        val atual = assertThrows<NotFoundException> {
            topicoService.buscarPorId(id)
        }

        // Ação e Validação
        // Verifica se a exceção lançada é NotFoundException
        assertThat(atual).isInstanceOf(NotFoundException::class.java)
        // Verifica se ao buscar por um id inexistente, é lançada uma exceção NotFoundException
        assertFailsWith<NotFoundException> {
            topicoService.buscarPorId(id)
        }
    }
}