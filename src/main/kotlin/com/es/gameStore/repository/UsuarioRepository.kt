package com.es.gameStore.repository

import com.es.gameStore.model.Usuario
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UsuarioRepository : JpaRepository<Usuario, Long> {

    fun findByUsername(username: String): Optional<Usuario>
    fun findByEmail(email: String): Optional<Usuario>

}