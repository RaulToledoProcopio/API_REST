package com.es.gameStore.controller

import com.es.gameStore.service.TokenService
import com.es.gameStore.model.Usuario
import com.es.gameStore.service.UsuarioService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import javax.naming.AuthenticationException

@RestController
@RequestMapping("/usuarios")
class UsuarioController {

    @Autowired
    private lateinit var usuarioService: UsuarioService

    @Autowired
    private lateinit var authenticationManager: AuthenticationManager

    @Autowired
    private lateinit var tokenService: TokenService

    // Registrar un usuario
    @PostMapping("/register")
    fun register(@RequestBody usuario: Usuario): ResponseEntity<Usuario> {
        val nuevoUsuario = usuarioService.crearUsuario(usuario)
        return ResponseEntity(nuevoUsuario, HttpStatus.CREATED)
    }

    // Login de un usuario (generación de token)
    @PostMapping("/login")
    fun login(@RequestBody usuario: Usuario): ResponseEntity<Any> {
        val authentication: Authentication
        return try {
            authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(usuario.nombre, usuario.contraseña)
            )
            val token = tokenService.generarToken(authentication)
            ResponseEntity(mapOf("token" to token), HttpStatus.OK)
        } catch (e: AuthenticationException) {
            ResponseEntity(mapOf("mensaje" to "Credenciales incorrectas"), HttpStatus.UNAUTHORIZED)
        }
    }

    // Listar todos los usuarios (solo admins)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    fun getUsuarios(): ResponseEntity<List<Usuario>> {
        val usuarios = usuarioService.getAllUsuarios()
        return if (usuarios.isNotEmpty()) {
            ResponseEntity(usuarios, HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NO_CONTENT)
        }
    }

    // Obtener detalles de un usuario específico (cualquier usuario autenticado puede ver su propio perfil o admins pueden ver cualquier perfil)
    @PreAuthorize("hasRole('ROLE_ADMIN') or #id == principal.id")
    @GetMapping("/{id}")
    fun getUsuarioById(@PathVariable id: Long): ResponseEntity<Usuario> {
        val usuario = usuarioService.getUsuarioById(id)
        return if (usuario != null) {
            ResponseEntity(usuario, HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    // Actualizar información de un usuario (el propio usuario o admin puede actualizarlo)
    @PreAuthorize("hasRole('ROLE_ADMIN') or #id == principal.id")
    @PutMapping("/{id}")
    fun updateUsuario(@PathVariable id: Long, @RequestBody usuario: Usuario): ResponseEntity<Usuario> {
        val updatedUsuario = usuarioService.updateUsuario(id, usuario)
        return if (updatedUsuario != null) {
            ResponseEntity(updatedUsuario, HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    // Eliminar un usuario (solo admins)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    fun deleteUsuarioById(@PathVariable id: Long): ResponseEntity<Any> {
        return if (usuarioService.deleteUsuarioById(id)) {
            ResponseEntity(HttpStatus.NO_CONTENT)
        } else {
            ResponseEntity(mapOf("mensaje" to "Usuario no encontrado"), HttpStatus.NOT_FOUND)
        }
    }
}