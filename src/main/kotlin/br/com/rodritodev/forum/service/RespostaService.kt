package br.com.rodritodev.forum.service

import br.com.rodritodev.forum.dto.AtualizacaoTopicoForm
import br.com.rodritodev.forum.dto.NovaRespostaForm
import br.com.rodritodev.forum.dto.RespostaView
import br.com.rodritodev.forum.mapper.RespostaFormMapper
import br.com.rodritodev.forum.mapper.RespostaViewMapper
import br.com.rodritodev.forum.model.Resposta
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
}
