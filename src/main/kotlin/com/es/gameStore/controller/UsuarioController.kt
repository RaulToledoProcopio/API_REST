package com.es.gameStore.controller

import com.es.gameStore.service.TokenService
import com.es.gameStore.model.Usuario
import com.es.gameStore.service.UsuarioService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/usuarios")
class UsuarioController(
    @Autowired val usuarioService: UsuarioService,
    @Autowired val tokenService: TokenService,
    @Autowired val authenticationManager: AuthenticationManager
) {

    // Crear un nuevo usuario
    @PostMapping
    fun crearUsuario(@RequestBody usuario: Usuario): Usuario {
        return usuarioService.crearUsuario(usuario)
    }

    // Listar todos los usuarios
    @GetMapping
    fun listarUsuarios(): List<Usuario> {
        return usuarioService.listarUsuarios()
    }

    // Obtener un usuario por su ID
    @GetMapping("/{id}")
    fun obtenerUsuario(@PathVariable id: Long): Usuario {
        return usuarioService.obtenerUsuario(id)
    }

    // Actualizar un usuario
    @PutMapping("/{id}")
    fun actualizarUsuario(@PathVariable id: Long, @RequestBody usuario: Usuario): Usuario {
        return usuarioService.actualizarUsuario(id, usuario)
    }

    // Eliminar un usuario
    @DeleteMapping("/{id}")
    fun eliminarUsuario(@PathVariable id: Long) {
        usuarioService.eliminarUsuario(id)
    }

    // Endpoint de login para obtener el token
    @PostMapping("/login")
    fun login(@RequestParam email: String, @RequestParam password: String): Map<String, String> {
        // Autenticar al usuario con las credenciales proporcionadas
        val authentication: Authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(email, password)
        )

        // Si las credenciales son correctas, generamos el token
        val token = tokenService.generarToken(authentication)

        // Retornamos el token
        return mapOf("token" to token)
    }
}
