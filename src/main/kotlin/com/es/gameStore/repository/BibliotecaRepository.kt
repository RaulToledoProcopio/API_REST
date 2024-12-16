package com.es.gameStore.repository

import com.es.gameStore.model.Biblioteca
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BibliotecaRepository : JpaRepository<Biblioteca, Long> {
    fun findByUsuarioId(usuarioId: Long): List<Biblioteca>
}
