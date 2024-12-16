package com.es.gameStore.controller

import com.es.gameStore.model.Videojuego
import com.es.gameStore.service.VideojuegoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/videojuegos")
class VideojuegoController(@Autowired val videojuegoService: VideojuegoService) {

    // Crear un nuevo videojuego (solo admins)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    fun crearVideojuego(@RequestBody videojuego: Videojuego): ResponseEntity<Videojuego> {
        val nuevoVideojuego = videojuegoService.crearVideojuego(videojuego)
        return ResponseEntity(nuevoVideojuego, HttpStatus.CREATED)
    }

    // Listar todos los videojuegos disponibles (cualquier usuario autenticado)
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @GetMapping
    fun listarVideojuegos(): ResponseEntity<List<Videojuego>> {
        val videojuegos = videojuegoService.listarVideojuegos()
        return if (videojuegos.isNotEmpty()) {
            ResponseEntity(videojuegos, HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NO_CONTENT)
        }
    }

    // Obtener detalles de un videojuego específico (cualquier usuario autenticado)
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    fun obtenerVideojuego(@PathVariable id: Long): ResponseEntity<Videojuego> {
        val videojuego = videojuegoService.obtenerVideojuego(id)
        return if (videojuego != null) {
            ResponseEntity(videojuego, HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    // Actualizar información de un videojuego (solo admins)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    fun actualizarVideojuego(@PathVariable id: Long, @RequestBody videojuego: Videojuego): ResponseEntity<Videojuego> {
        val videojuegoActualizado = videojuegoService.actualizarVideojuego(id, videojuego)
        return ResponseEntity(videojuegoActualizado, HttpStatus.OK)
    }

    // Eliminar un videojuego (solo admins)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    fun eliminarVideojuego(@PathVariable id: Long): ResponseEntity<Void> {
        return try {
            videojuegoService.eliminarVideojuego(id)
            ResponseEntity(HttpStatus.NO_CONTENT)
        } catch (e: Exception) {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }
}