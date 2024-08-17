package br.com.rodritodev.forum.service

import br.com.rodritodev.forum.dto.AtualizacaoRespostaForm
import br.com.rodritodev.forum.dto.AtualizacaoTopicoForm
import br.com.rodritodev.forum.dto.NovaRespostaForm
import br.com.rodritodev.forum.dto.RespostaView
import br.com.rodritodev.forum.mapper.RespostaFormMapper
import br.com.rodritodev.forum.mapper.RespostaViewMapper
import br.com.rodritodev.forum.model.StatusTopico
import org.springframework.stereotype.Service

/**
 * Serviço de respostas dos tópicos do fórum
 */
@Service
class RespostaService(
    private var topicoService: TopicoService,
    private var usuarioService: UsuarioService,
    private val respostaFormMapper: RespostaFormMapper,
    private val respostaViewMapper: RespostaViewMapper,
) {
    /**
     * Lista as respostas de um tópico
     * @param idTopico Id do tópico
     * @return Lista de respostas
     */
    fun listar(idTopico: Long): List<RespostaView> {
        val respostas = topicoService.buscarPorId(idTopico).respostas

        if (respostas.isEmpty()) {
            throw IllegalArgumentException("Tópico ($idTopico) não possui respostas")
        }

        return respostas
    }

    /**
     * Cadastra uma nova resposta
     * @param novaRespostaForm Dados da resposta
     * @return Resposta cadastrada
     */
    fun cadastrar(novaRespostaForm: NovaRespostaForm): RespostaView {
        val topicoView = topicoService.buscarPorId(novaRespostaForm.idTopico)

        if (topicoView.status == StatusTopico.FECHADO) {
            throw IllegalStateException("Tópico não pode receber respostas pois está fechado")
        }
        val resposta = respostaFormMapper.map(novaRespostaForm)

        val autor = usuarioService.buscarPorId(novaRespostaForm.idAutor)
        resposta.id = topicoView.respostas.size.toLong() + 1
        resposta.usuario = autor
        resposta.topico = topicoView
        topicoView.respostas = topicoView.respostas.plus(respostaViewMapper.map(resposta))

        topicoService.atualizar(
            AtualizacaoTopicoForm(
                id = topicoView.id!!,
                titulo = topicoView.titulo,
                mensagem = topicoView.mensagem,
                respostas = topicoView.respostas
            )
        )
        return respostaViewMapper.map(resposta)
    }

    /**
     * Atualiza uma resposta
     * @param atualizacaoRespostaForm Dados da resposta
     * @return Resposta atualizada
     */
    fun atualizar(atualizacaoRespostaForm: AtualizacaoRespostaForm): RespostaView {
        val topicoView = topicoService.buscarPorId(atualizacaoRespostaForm.idTopico)

        val resposta = topicoView.respostas.find { it.id == atualizacaoRespostaForm.idResposta }

        if (resposta == null) {
            throw IllegalArgumentException("Resposta (${atualizacaoRespostaForm.idResposta}) não encontrada")
        }

        topicoView.respostas = topicoView.respostas.minus(resposta)

        resposta.mensagem = atualizacaoRespostaForm.mensagem
        resposta.solucao = atualizacaoRespostaForm.solucao ?: false

        topicoView.respostas = topicoView.respostas.plus(resposta)

        topicoService.atualizar(
            AtualizacaoTopicoForm(
                id = topicoView.id!!,
                titulo = topicoView.titulo,
                mensagem = topicoView.mensagem,
                respostas = topicoView.respostas
            )
        )

        return resposta
    }

    /**
     * Marca uma resposta como solução
     * @param id Id da resposta
     * @return Resposta marcada como solução
     */
    fun marcarComoSolucao(id: Long): RespostaView? {
        val resposta = topicoService.buscarPorId(id).respostas.find { it.solucao }

        if (resposta != null) {
            throw IllegalStateException("Tópico já possui uma resposta marcada como solução")
        }

        val respostaView = topicoService.buscarPorId(id).respostas.find { it.id == id }

        if (respostaView == null) {
            throw IllegalArgumentException("Resposta ($id) não encontrada")
        }

        respostaView.solucao = true

        return respostaView
    }

    /**
     * Remove a marcação de solução de uma resposta
     * @param id Id da resposta
     * @return Resposta com a marcação de solução removida
     */
    fun removerSolucao(id: Long): RespostaView? {
        val respostaView = topicoService.buscarPorId(id).respostas.find { it.id == id }

        if (respostaView == null) {
            throw IllegalArgumentException("Resposta ($id) não encontrada")
        }

        respostaView.solucao = false

        return respostaView
    }

    /**
     * Deleta uma resposta
     * @param idTopico Id do tópico
     * @param idResposta Id da resposta
     */
    fun deletar(idTopico: Long, idResposta: Long) {
        val topicoView = topicoService.buscarPorId(idTopico)

        if (topicoView.status == StatusTopico.FECHADO) {
            throw IllegalStateException("Tópico não pode receber respostas pois está fechado")
        }

        if (topicoView.respostas.isEmpty()) {
            throw IllegalArgumentException("Tópico ($idTopico) não possui respostas")
        }

        if (topicoView.respostas.size == 1) {
            throw IllegalStateException("Tópico não pode ficar sem respostas")
        }

        val resposta = topicoView.respostas.find { it.id == idResposta }

        if (resposta == null) {
            throw IllegalArgumentException("Resposta ($idResposta) não encontrada")
        }

        if (resposta.solucao) {
            throw IllegalStateException("Resposta marcada como solução não pode ser deletada")
        }

        topicoView.respostas = topicoView.respostas.minus(resposta)

        topicoService.atualizar(
            AtualizacaoTopicoForm(
                id = topicoView.id!!,
                titulo = topicoView.titulo,
                mensagem = topicoView.mensagem,
                respostas = topicoView.respostas
            )
        )
    }
}
