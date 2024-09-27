package br.com.rodritodev.forum.controller

import br.com.rodritodev.forum.model.Curso
import br.com.rodritodev.forum.service.CursoService
import jakarta.transaction.Transactional
import jakarta.validation.Valid
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder

/**
 * Controlador de cursos do fórum
 */
@RestController // Indica que a classe é um controlador REST
@RequestMapping("/cursos") // Indica o caminho base para as requisições
class CursoController(private val cursoService: CursoService) {

    /**
     * Lista todos os cursos ou filtra por nome do curso
     * @return Lista de cursos
     */
    @GetMapping // Indica que o método responde a requisições GET
    @Cacheable("cursosEmCache") // Habilita o cache para a lista de cursos
    @Transactional
    fun listar(
        @RequestParam(required = false) nomeCurso: String?, // Parâmetro opcional para filtrar por nome do curso
        @PageableDefault(size = 10, sort = ["nome"], direction = Sort.Direction.ASC) pageable: Pageable // Parâmetros de paginação
    ): Page<Curso> {
        return cursoService.listar(nomeCurso, pageable)
    }

    /**
     * Busca um curso pelo id
     * @param id Id do curso
     * @return Curso encontrado
     */
    @GetMapping("/{id}")
    @Transactional
    fun buscarPorId(@PathVariable id: Long): Curso {
        return cursoService.buscarPorId(id)
    }

    /**
     * Cadastra um novo curso
     * @param dto Dados do curso
     * @return Curso cadastrado e URI do novo curso
     */
    @PostMapping
    @Transactional // Indica que a transação será aberta e fechada automaticamente
    @CacheEvict(value = ["cursosEmCache"], allEntries = true) // Limpa o cache de cursos ao cadastrar um novo curso
    fun cadastrar(
        @RequestBody @Valid dto: Curso,
        uriBuilder: UriComponentsBuilder
    ): ResponseEntity<Curso> {
        val curso = cursoService.cadastrar(dto)

        // Cria a URI do novo curso criado para ser retornada no cabeçalho Location
        val uri = uriBuilder.path("/cursos/${curso.id}").build().toUri()
        return ResponseEntity.created(uri).body(curso)
    }

    /**
     * Atualiza um curso
     * @param dto Dados do curso
     * @return Curso atualizado
     */
    @PutMapping
    @Transactional
    @CacheEvict(value = ["cursosEmCache"], allEntries = true)
    fun atualizar(@RequestBody @Valid dto: Curso): ResponseEntity<Curso> {
        val curso = cursoService.atualizar(dto)
        return ResponseEntity.ok(curso)
    }

    /**
     * Deleta um curso
     * @param id Id do curso
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // Indica que a resposta será um status 204 No Content
    @Transactional
    @CacheEvict(value = ["cursosEmCache"], allEntries = true)
    fun deletar(@PathVariable id: Long){
        cursoService.deletar(id)
    }
}