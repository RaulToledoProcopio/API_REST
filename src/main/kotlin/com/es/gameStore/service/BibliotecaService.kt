package com.es.gameStore.service

import com.es.gameStore.model.Biblioteca
import com.es.gameStore.repository.BibliotecaRepository
import com.es.gameStore.repository.UsuarioRepository
import com.es.gameStore.repository.VideojuegoRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class BibliotecaService(
    @Autowired private val bibliotecaRepository: BibliotecaRepository,
    @Autowired private val usuarioRepository: UsuarioRepository,
    @Autowired private val videojuegoRepository: VideojuegoRepository
) {
    fun agregarVideojuego(biblioteca: Biblioteca): Biblioteca {
        // Validar que el usuario exista
        usuarioRepository.findById(biblioteca.idUsuario.idUsuario!!)
            .orElseThrow { IllegalArgumentException("Usuario no encontrado") }

        // Validar que el videojuego exista
        videojuegoRepository.findById(biblioteca.idVideojuego.idVideojuego!!)
            .orElseThrow { IllegalArgumentException("Videojuego no encontrado") }

        // Guardar el registro en la tabla "biblioteca"
        return bibliotecaRepository.save(biblioteca)
    }

    fun obtenerBiblioteca(idUsuario: Long): List<Biblioteca> {
        return bibliotecaRepository.findAll().filter { it.idUsuario.idUsuario == idUsuario }
    }

    fun eliminarVideojuego(idBiblioteca: Long) {
        val biblioteca = bibliotecaRepository.findById(idBiblioteca)
            .orElseThrow { IllegalArgumentException("Registro de biblioteca no encontrado") }
        bibliotecaRepository.delete(biblioteca)
    }
}