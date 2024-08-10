package br.com.rodritodev.forum.service

import br.com.rodritodev.forum.model.Curso
import org.springframework.stereotype.Service

@Service
class CursoService(var cursos: List<Curso>) {

    init {
        cursos = listOf(
            Curso(
                id = 1,
                nome = "Kotlin",
                categoria = "Programação",
            ),
            Curso(
                id = 2,
                nome = "Java",
                categoria = "Programação",
            ),
            Curso(
                id = 3,
                nome = "HTML e CSS",
                categoria = "Front-end",
            ),
        )
    }

    fun buscarPorId(id: Long): Curso {
        cursos.forEach {
            if (it.id == id) {
                return it
            }
        }
        throw IllegalArgumentException("Curso ($id) não encontrado")
    }
}
