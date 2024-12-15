package com.es.gameStore.controller

import com.es.gameStore.model.Videojuego
import com.es.gameStore.service.VideojuegoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/videojuegos")
class VideojuegoController(@Autowired val videojuegoService: VideojuegoService) {

    // Crear un nuevo videojuego (solo admins)
    @PostMapping
    fun crearVideojuego(@RequestBody videojuego: Videojuego): Videojuego {
        return videojuegoService.crearVideojuego(videojuego)
    }

    // Listar todos los videojuegos disponibles
    @GetMapping
    fun listarVideojuegos(): List<Videojuego> {
        return videojuegoService.listarVideojuegos()
    }

    // Obtener detalles de un videojuego específico
    @GetMapping("/{id}")
    fun obtenerVideojuego(@PathVariable id: Long): Videojuego {
        return videojuegoService.obtenerVideojuego(id)
    }

    // Actualizar información de un videojuego (solo admins)
    @PutMapping("/{id}")
    fun actualizarVideojuego(@PathVariable id: Long, @RequestBody videojuego: Videojuego): Videojuego {
        return videojuegoService.actualizarVideojuego(id, videojuego)
    }

    // Eliminar un videojuego (solo admins)
    @DeleteMapping("/{id}")
    fun eliminarVideojuego(@PathVariable id: Long) {
        videojuegoService.eliminarVideojuego(id)
    }
}
