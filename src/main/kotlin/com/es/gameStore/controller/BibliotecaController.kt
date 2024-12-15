package com.es.gameStore.controller

import com.es.gameStore.model.Biblioteca
import com.es.gameStore.service.BibliotecaService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/biblioteca")
class BibliotecaController(@Autowired val bibliotecaService: BibliotecaService) {

    // Agregar un videojuego a la biblioteca de un usuario (solo admins)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    fun agregarVideojuego(@RequestBody biblioteca: Biblioteca): ResponseEntity<Biblioteca> {
        val videojuegoAgregado = bibliotecaService.agregarVideojuego(biblioteca)
        return ResponseEntity(videojuegoAgregado, HttpStatus.CREATED)
    }

    // Obtener la lista de videojuegos en la biblioteca de un usuario (cualquier usuario autenticado puede ver su propia biblioteca)
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/{id_usuario}")
    fun obtenerBiblioteca(@PathVariable id_usuario: Long): ResponseEntity<List<Biblioteca>> {
        val biblioteca = bibliotecaService.obtenerBiblioteca(id_usuario)
        return if (biblioteca.isNotEmpty()) {
            ResponseEntity(biblioteca, HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NO_CONTENT)
        }
    }

    // Eliminar un videojuego de la biblioteca de un usuario (solo admins o el propio usuario)
    @PreAuthorize("hasRole('ROLE_ADMIN') or @bibliotecaService.isOwnerOfBiblioteca(#id_biblioteca, principal.username)")
    @DeleteMapping("/{id_biblioteca}")
    fun eliminarVideojuego(@PathVariable id_biblioteca: Long): ResponseEntity<Void> {
        try {
            bibliotecaService.eliminarVideojuego(id_biblioteca)
            return ResponseEntity(HttpStatus.NO_CONTENT)
        } catch (e: Exception) {
            return ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }
}
