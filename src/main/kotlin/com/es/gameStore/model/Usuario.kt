package com.es.gameStore.model

import jakarta.persistence.*

@Entity
@Table(name = "usuarios")
data class Usuario(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false, unique = true)
    var nombre: String,

    @Column(nullable = false, unique = true)
    var email: String,

    @Column(nullable = false)
    var contraseña: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var rol: Rol = Rol.CLIENTE
)

enum class Rol {
    CLIENTE,
    ADMIN
}