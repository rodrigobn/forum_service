package br.com.rodritodev.forum.repository

import br.com.rodritodev.forum.model.Curso
import org.springframework.data.jpa.repository.JpaRepository

interface CursoRepository: JpaRepository<Curso, Long>