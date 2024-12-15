package com.es.gameStore.controller

import com.es.gameStore.model.Usuario
import com.es.gameStore.service.UsuarioService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/usuarios")
class UsuarioController(@Autowired val usuarioService: UsuarioService) {

    @PostMapping
    fun crearUsuario(@RequestBody usuario: Usuario): Usuario {
        return usuarioService.crearUsuario(usuario)
    }

    @GetMapping
    fun listarUsuarios(): List<Usuario> {
        return usuarioService.listarUsuarios()
    }

    @GetMapping("/{id}")
    fun obtenerUsuario(@PathVariable id: Long): Usuario {
        return usuarioService.obtenerUsuario(id)
    }

    @PutMapping("/{id}")
    fun actualizarUsuario(@PathVariable id: Long, @RequestBody usuario: Usuario): Usuario {
        return usuarioService.actualizarUsuario(id, usuario)
    }

    @DeleteMapping("/{id}")
    fun eliminarUsuario(@PathVariable id: Long) {
        usuarioService.eliminarUsuario(id)
    }
}