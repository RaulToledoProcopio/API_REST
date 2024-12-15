package com.es.gameStore.service

import com.es.gameStore.model.Usuario
import com.es.gameStore.repository.UsuarioRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UsuarioService(@Autowired val usuarioRepository: UsuarioRepository) {

    fun crearUsuario(usuario: Usuario): Usuario {
        return usuarioRepository.save(usuario)
    }

    fun listarUsuarios(): List<Usuario> {
        return usuarioRepository.findAll()
    }

    fun obtenerUsuario(id: Long): Usuario {
        return usuarioRepository.findById(id).orElseThrow { Exception("Usuario no encontrado") }
    }

    fun actualizarUsuario(id: Long, usuario: Usuario): Usuario {
        val usuarioExistente = obtenerUsuario(id)
        usuarioExistente.nombre = usuario.nombre
        usuarioExistente.email = usuario.email
        usuarioExistente.contraseña = usuario.contraseña
        usuarioExistente.rol = usuario.rol
        return usuarioRepository.save(usuarioExistente)
    }

    fun eliminarUsuario(id: Long) {
        usuarioRepository.deleteById(id)
    }
}