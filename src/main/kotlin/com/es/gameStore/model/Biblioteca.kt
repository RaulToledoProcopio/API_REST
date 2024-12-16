package com.es.gameStore.model

import jakarta.persistence.*

@Entity
@Table(name = "biblioteca")
data class Biblioteca(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_biblioteca")
    val idBiblioteca: Long? = null,

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    val idUsuario: Usuario,

    @ManyToOne
    @JoinColumn(name = "id_videojuego", nullable = false)
    val idVideojuego: Videojuego
)