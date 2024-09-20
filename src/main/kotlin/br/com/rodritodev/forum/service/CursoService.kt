package br.com.rodritodev.forum.service

import br.com.rodritodev.forum.exception.DataIntegrityViolationException
import br.com.rodritodev.forum.exception.NotFoundException
import br.com.rodritodev.forum.model.Curso
import br.com.rodritodev.forum.repository.CursoRepository
import br.com.rodritodev.forum.repository.TopicoRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

/**
 * Serviço de cursos do fórum
 */
@Service
class CursoService(
    private val repository: CursoRepository,
    private val topicoRepository: TopicoRepository,
) {

    /**
     * Lista todos os cursos ou filtra por nome do curso
     * @return Lista de cursos
     */
    fun listar(
        nomeCurso: String?,
        pageable: Pageable
    ): Page<Curso> {
        if (nomeCurso != null) {
            return repository.findByNomeContaining(nomeCurso, pageable)
        }
        return repository.findAll(pageable)
    }

    /**
     * Busca um curso pelo id
     * @param id Id do curso
     * @return Curso encontrado
     */
    fun buscarPorId(id: Long): Curso {
        return repository.findById(id).orElseThrow {
            IllegalArgumentException("Curso ($id) não encontrado")
        }
    }

    /**
     * Cadastra um novo curso
     * @param curso Dados do curso
     * @return Curso cadastrado
     */
    fun cadastrar(curso: Curso): Curso {
        repository.save(curso)
        return curso
    }

    /**
     * Deleta um curso
     * @param id Id do curso
     */
    fun deletar(id: Long) {
        val topicos = topicoRepository.findAll()
        topicos.forEach {
            if (it.curso.id == id) {
                throw DataIntegrityViolationException("Curso com ID $id está sendo referenciado por tópicos existentes e não pode ser deletado.")
            }
        }
        repository.findById(id).orElseThrow {
            NotFoundException("Curso ($id) não encontrado")
        }
        repository.deleteById(id)
    }

    /**
     * Atualiza um curso
     * @param dto Dados do curso
     * @return Curso atualizado
     */
    fun atualizar(dto: Curso): Curso {
        val curso = repository.findById(dto.id).orElseThrow {
            IllegalArgumentException("Curso (${dto.id}) não encontrado")
        }
        curso.nome = dto.nome
        curso.categoria = dto.categoria
        repository.save(curso)
        return curso
    }
}
