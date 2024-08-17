package br.com.rodritodev.forum.controller

import br.com.rodritodev.forum.dto.AtualizacaoRespostaForm
import br.com.rodritodev.forum.dto.NovaRespostaForm
import br.com.rodritodev.forum.dto.RespostaView
import br.com.rodritodev.forum.service.RespostaService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder

/**
 * Controlador de respostas dos tópicos do fórum
 */
@RestController
@RequestMapping("/respostas")
class RespostaController(private val respostaService: RespostaService) {

    /**
     * Lista as respostas de um tópico
     * @param id Id do tópico
     * @return Lista de respostas
     */
    @GetMapping("/topico/{id}")
    fun listar(@PathVariable id: Long): List<RespostaView> {
        return respostaService.listar(id)
    }

    /**
     * Cadastra uma nova resposta
     * @param resposta Dados da resposta
     * @return Resposta cadastrada e URI da nova resposta
     */
    @PostMapping
    fun cadastrar(
        @RequestBody @Valid resposta: NovaRespostaForm,
        uriComponentsBuilder: UriComponentsBuilder
    ): ResponseEntity<RespostaView> {
        val respostaCadastrada = respostaService.cadastrar(resposta)

        val uri = uriComponentsBuilder.path("/respostas/${respostaCadastrada.id}").build().toUri()
        return ResponseEntity.created(uri).body(respostaCadastrada)
    }

    /**
     * Atualiza uma resposta
     * @param resposta Dados da resposta
     * @return Resposta atualizada
     */
    @PutMapping
    fun atualizar(@RequestBody @Valid atualizacaoRespostaForm: AtualizacaoRespostaForm): ResponseEntity<RespostaView> {
        val respostaView = respostaService.atualizar(atualizacaoRespostaForm)
        return ResponseEntity.ok(respostaView)
    }

    /**
     * Marca uma resposta como solução
     * @param id Id da resposta
     * @return Resposta marcada como solução
     */
    @PutMapping("/{id}/solucao")
    fun marcarComoSolucao(@PathVariable id: Long): ResponseEntity<RespostaView> {
        // TODO melhorar a busca da resposta
        val respostaView = respostaService.marcarComoSolucao(id)
        return ResponseEntity.ok(respostaView)
    }

    /**
     * Remove a marcação de solução de uma resposta
     * @param id Id da resposta
     * @return Resposta com a marcação de solução removida
     */
    @DeleteMapping("/{id}/solucao")
    fun removerSolucao(@PathVariable id: Long): ResponseEntity<RespostaView> {
        // TODO melhorar a busca da resposta
        val respostaView = respostaService.removerSolucao(id)
        return ResponseEntity.ok(respostaView)
    }

    /**
     * Deleta uma resposta
     * @param id Id da resposta
     */
    @DeleteMapping("/{idTopico}/{idResposta}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deletar(@PathVariable idTopico: Long, @PathVariable idResposta: Long) {
        respostaService.deletar(idTopico, idResposta)
    }
}