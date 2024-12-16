package com.es.gameStore.service

import com.es.gameStore.model.Videojuego
import com.es.gameStore.repository.VideojuegoRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class VideojuegoService(@Autowired private val videojuegoRepository: VideojuegoRepository) {

    // Crear un nuevo videojuego
    fun crearVideojuego(videojuego: Videojuego): Videojuego {
        return videojuegoRepository.save(videojuego)
    }

    // Listar todos los videojuegos
    fun listarVideojuegos(): List<Videojuego> {
        return videojuegoRepository.findAll()
    }

    // Obtener un videojuego por ID
    fun obtenerVideojuego(id: Long): Videojuego {
        return videojuegoRepository.findById(id).orElseThrow { RuntimeException("Videojuego no encontrado") }
    }

    // Actualizar un videojuego
    fun actualizarVideojuego(id: Long, videojuego: Videojuego): Videojuego {
        if (!videojuegoRepository.existsById(id)) {
            throw RuntimeException("Videojuego no encontrado")
        }
        return videojuegoRepository.save(videojuego)
    }

    // Eliminar un videojuego
    fun eliminarVideojuego(id: Long) {
        if (!videojuegoRepository.existsById(id)) {
            throw RuntimeException("Videojuego no encontrado")
        }
        videojuegoRepository.deleteById(id)
    }
}
