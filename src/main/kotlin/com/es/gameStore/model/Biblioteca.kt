package com.es.gameStore.model

import jakarta.persistence.*

@Entity
@Table(name = "biblioteca")
data class Biblioteca(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var idBiblioteca: Long? = null,

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    var idUsuario: Usuario,

    @ManyToOne
    @JoinColumn(name = "id_videojuego", nullable = false)
    var idVideojuego: Videojuego,

    @Column(nullable = false)
    var fechaAdquisicion: String
)