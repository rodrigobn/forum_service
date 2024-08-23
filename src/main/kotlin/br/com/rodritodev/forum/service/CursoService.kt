package br.com.rodritodev.forum.service

import br.com.rodritodev.forum.model.Curso
import br.com.rodritodev.forum.repository.CursoRepository
import org.springframework.stereotype.Service

/**
 * Serviço de cursos do fórum
 */
@Service
class CursoService(private val repository: CursoRepository) {

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
     * Lista todos os cursos
     * @return Lista de cursos
     */
    fun listar(): List<Curso> {
        return repository.findAll().toList()
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
        repository.findById(id).orElseThrow {
            Exception("Curso ($id) não encontrado")
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
