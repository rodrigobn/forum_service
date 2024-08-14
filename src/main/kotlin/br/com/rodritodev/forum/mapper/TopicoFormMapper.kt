package br.com.rodritodev.forum.mapper

import br.com.rodritodev.forum.dto.NovoTopicoForm
import br.com.rodritodev.forum.model.Topico
import br.com.rodritodev.forum.service.CursoService
import br.com.rodritodev.forum.service.UsuarioService
import org.springframework.stereotype.Component

/**
 * Mapper de formulário de tópicos para tópicos
 *
 * Mapper é uma classe que transforma um objeto em outro
 */
@Component
class TopicoFormMapper(
    private val cursoService: CursoService,
    private val usuarioService: UsuarioService,
) : Mapper<NovoTopicoForm, Topico> {
    override fun map(t: NovoTopicoForm): Topico {
        return Topico(
            titulo = t.titulo,
            mensagem = t.mensagem,
            curso = cursoService.buscarPorId(t.idCurso),
            autor = usuarioService.buscarPorId(t.idAutor),
        )
    }

}
