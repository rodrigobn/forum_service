package br.com.rodritodev.forum.service

import br.com.rodritodev.forum.dto.AtualizacaoRespostaForm
import br.com.rodritodev.forum.dto.NovaRespostaForm
import br.com.rodritodev.forum.dto.RespostaView
import br.com.rodritodev.forum.exception.NotFoundException
import br.com.rodritodev.forum.mapper.RespostaViewMapper
import br.com.rodritodev.forum.model.Resposta
import br.com.rodritodev.forum.model.StatusTopico
import br.com.rodritodev.forum.repository.RespostaRepository
import br.com.rodritodev.forum.repository.TopicoRepository
import org.springframework.stereotype.Service

/**
 * Serviço de respostas dos tópicos do fórum
 */
@Service
class RespostaService(
    private val topicoRepository: TopicoRepository,
    private val respostaRepository: RespostaRepository,
    private var usuarioService: UsuarioService,
    private val respostaViewMapper: RespostaViewMapper,
) {
    /**
     * Lista as respostas de um tópico
     * @param idTopico Id do tópico
     * @return Lista de respostas
     */
    fun listar(idTopico: Long): List<RespostaView> {
        val topico = topicoRepository.findById(idTopico).orElseThrow {
            NotFoundException("Tópico não encontrado")
        }

        return topico.resposta.map { respostaViewMapper.map(it) }
    }

    /**
     * Cadastra uma nova resposta
     * @param novaRespostaForm Dados da resposta
     * @return Resposta cadastrada
     */
    fun cadastrar(novaRespostaForm: NovaRespostaForm): RespostaView {
        val topico = topicoRepository.findById(novaRespostaForm.idTopico).orElseThrow {
            NotFoundException("Tópico não encontrado")
        }

        if (topico.status == StatusTopico.FECHADO) {
            throw Exception("Tópico não pode receber respostas pois está fechado")
        }

        val autor = usuarioService.buscarPorId(novaRespostaForm.idAutor)

        val resposta = Resposta(
            mensagem = novaRespostaForm.mensagem,
            usuario = autor,
            topico = topico,
            solucao = false
        )
        val respostaSalva = respostaRepository.save(resposta)

        topico.resposta = topico.resposta.plus(respostaSalva)
        topicoRepository.save(topico)

        return respostaViewMapper.map(respostaSalva)
    }

    /**
     * Atualiza uma resposta
     * @param atualizacaoRespostaForm Dados da resposta
     * @return Resposta atualizada
     */
    fun atualizar(atualizacaoRespostaForm: AtualizacaoRespostaForm): RespostaView {
        val topico = topicoRepository.findById(atualizacaoRespostaForm.idTopico).orElseThrow {
            NotFoundException("Tópico não encontrado")
        }

        if (topico.status == StatusTopico.FECHADO) {
            throw Exception("Tópico não pode receber respostas pois está fechado")
        }

        val respostaAtualizada = topico.resposta.firstOrNull { resposta ->
            resposta.id == atualizacaoRespostaForm.idResposta
        }

        if (respostaAtualizada == null) {
            throw Exception("Resposta não encontrada")
        }

        respostaAtualizada.mensagem = atualizacaoRespostaForm.mensagem
        respostaAtualizada.solucao = atualizacaoRespostaForm.solucao == true
        respostaAtualizada.topico = topico

        topico.resposta = topico.resposta.minus(respostaAtualizada).plus(respostaAtualizada)

        topicoRepository.save(topico)

        return respostaViewMapper.map(respostaAtualizada)
    }

    /**
     * Marca uma resposta como solução
     * @param idTopico Id do tópico
     * @param idResposta Id da resposta
     * @return Resposta marcada como solução
     */
    fun marcarComoSolucao(idTopico: Long, idResposta: Long): Resposta? {
        val topico = topicoRepository.findById(idTopico).orElseThrow {
            NotFoundException("Tópico não encontrado")
        }

        if (topico.status == StatusTopico.FECHADO) {
            throw Exception("Tópico não pode receber respostas pois está fechado")
        }

        val resposta = topico.resposta.find { it.id == idResposta }

        if (resposta == null) {
            throw Exception("Resposta ($idResposta) não encontrada")
        }

        resposta.solucao = true

        return resposta
    }

    /**
     * Remove a marcação de solução de uma resposta
     * @param idTopico Id do tópico
     * @param idResposta Id da resposta
     * @return Resposta com a marcação de solução removida
     */
    fun removerSolucao(idTopico: Long, idResposta: Long): Resposta? {
        val topico = topicoRepository.findById(idTopico).orElseThrow {
            NotFoundException("Tópico não encontrado")
        }

        if (topico.status == StatusTopico.FECHADO) {
            throw Exception("Tópico não pode receber respostas pois está fechado")
        }

        val resposta = topico.resposta.find { it.id == idResposta }

        if (resposta == null) {
            throw Exception("Resposta ($idResposta) não encontrada")
        }

        resposta.solucao = false

        return resposta
    }

    /**
     * Deleta uma resposta
     * @param idTopico Id do tópico
     * @param idResposta Id da resposta
     */
    fun deletar(idTopico: Long, idResposta: Long) {
        val topico = topicoRepository.findById(idTopico).orElseThrow {
            NotFoundException("Tópico não encontrado")
        }

        if (topico.status == StatusTopico.FECHADO) {
            throw Exception("Tópico não pode receber respostas pois está fechado")
        }

        if (topico.resposta.isEmpty()) {
            throw NotFoundException("Tópico ($idTopico) não possui respostas")
        }

        if (topico.resposta.size == 1) {
            throw Exception("Tópico não pode ficar sem respostas")
        }

        val resposta = topico.resposta.find { it.id == idResposta }

        if (resposta == null) {
            throw NotFoundException("Resposta não encontrada")
        }

        if (resposta.solucao) {
            throw Exception("Resposta marcada como solução não pode ser deletada")
        }

        topico.resposta = topico.resposta.minus(resposta)

        topicoRepository.save(topico)
    }
}
