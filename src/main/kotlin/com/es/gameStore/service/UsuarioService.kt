package com.es.gameStore.service

import com.es.gameStore.model.Usuario
import com.es.gameStore.repository.UsuarioRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service


@Service
class UsuarioService: UserDetailsService {

    @Autowired
    private lateinit var usuarioRepository: UsuarioRepository

    @Autowired
    private lateinit var passwordEncoder: BCryptPasswordEncoder

    override fun loadUserByUsername(username: String?): UserDetails {
        val usuario: Usuario = usuarioRepository
            .findByUsername(username!!)
            .orElseThrow { Exception("Usuario no encontrado") }

        return User
            .builder()
            .username(usuario.username)
            .password(usuario.password)
            .roles(usuario.roles)
            .build()
    }

    fun crearUsuario (usuario: Usuario): Usuario {

        val existingEmail = usuarioRepository.findByEmail(usuario.email!!)
        if (existingEmail.isPresent) {
            throw Exception("El correo ya existe.")
        }

        val existingUser = usuarioRepository.findByUsername(usuario.username!!)
        if (existingUser.isPresent) {
            throw Exception("El usuario ya existe")
        }

        usuario.password = passwordEncoder.encode(usuario.password)

        return usuarioRepository.save(usuario)
    }




    fun getUsuarioById(id: Long): Usuario? {
        return usuarioRepository.findById(id).orElse(null)
    }

    fun getAllUsuarios(): List<Usuario> {
        return usuarioRepository.findAll()
    }

    fun updateUsuario(id: Long, usuario: Usuario): Usuario? {
        val existingUsuario = usuarioRepository.findById(id)
        if (existingUsuario.isPresent) {
            val usuarioToUpdate = existingUsuario.get()
            usuarioToUpdate.username = usuario.username ?: usuarioToUpdate.username
            usuarioToUpdate.email = usuario.email ?: usuarioToUpdate.email
            if (!usuario.password.isNullOrBlank()) {
                usuarioToUpdate.password = passwordEncoder.encode(usuario.password)
            }
            usuarioToUpdate.roles = usuario.roles ?: usuarioToUpdate.roles
            return usuarioRepository.save(usuarioToUpdate)
        }
        return null
    }

    fun deleteUsuarioById(id: Long): Boolean {
        return if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id)
            true
        } else {
            false
        }
    }
}