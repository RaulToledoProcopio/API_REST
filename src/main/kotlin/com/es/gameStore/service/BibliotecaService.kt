package com.es.gameStore.service

import com.es.gameStore.model.Biblioteca
import com.es.gameStore.repository.BibliotecaRepository
import com.es.gameStore.repository.UsuarioRepository
import com.es.gameStore.repository.VideojuegoRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class BibliotecaService(
    @Autowired val bibliotecaRepository: BibliotecaRepository,
    @Autowired val videojuegoRepository: VideojuegoRepository,
    @Autowired val usuarioRepository: UsuarioRepository
) {

    // Agregar un videojuego a la biblioteca de un usuario
    fun agregarVideojuego(biblioteca: Biblioteca): Biblioteca {
        // Verificar si el usuario existe
        val usuario = usuarioRepository.findById(biblioteca.usuario.id)
        if (!usuario.isPresent) {
            throw Exception("Usuario no encontrado")
        }

        // Verificar si el videojuego existe
        val videojuego = videojuegoRepository.findById(biblioteca.videojuego.id)
        if (!videojuego.isPresent) {
            throw Exception("Videojuego no encontrado")
        }

        // Si el usuario y el videojuego existen, se agrega a la biblioteca
        return bibliotecaRepository.save(biblioteca)
    }

    // Obtener la lista de videojuegos en la biblioteca de un usuario
    fun obtenerBiblioteca(id_usuario: Long): List<Biblioteca> {
        return bibliotecaRepository.findByUsuarioId(id_usuario)
    }

    // Eliminar un videojuego de la biblioteca de un usuario
    fun eliminarVideojuego(id_biblioteca: Long) {
        bibliotecaRepository.deleteById(id_biblioteca)
    }
}
