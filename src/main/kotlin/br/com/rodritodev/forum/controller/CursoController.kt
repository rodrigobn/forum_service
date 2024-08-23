package br.com.rodritodev.forum.controller

import br.com.rodritodev.forum.model.Curso
import br.com.rodritodev.forum.service.CursoService
import jakarta.transaction.Transactional
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder

/**
 * Controlador de cursos do fórum
 */
@RestController
@RequestMapping("/cursos")
class CursoController(private val cursoService: CursoService) {

    /**
     * Lista todos os cursos
     * @return Lista de cursos
     */
    @GetMapping
    fun listar(): List<Curso> {
        return cursoService.listar()
    }

    /**
     * Busca um curso pelo id
     * @param id Id do curso
     * @return Curso encontrado
     */
    @GetMapping("/{id}")
    fun buscarPorId(@PathVariable id: Long): Curso {
        return cursoService.buscarPorId(id)
    }

    /**
     * Cadastra um novo curso
     * @param dto Dados do curso
     * @return Curso cadastrado e URI do novo curso
     */
    @PostMapping
    @Transactional
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
    fun atualizar(@RequestBody @Valid dto: Curso): ResponseEntity<Curso> {
        val curso = cursoService.atualizar(dto)
        return ResponseEntity.ok(curso)
    }

    /**
     * Deleta um curso
     * @param id Id do curso
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    fun deletar(@PathVariable id: Long){
        cursoService.deletar(id)
    }
}