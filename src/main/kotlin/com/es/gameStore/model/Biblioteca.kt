package com.es.gameStore.model

import jakarta.persistence.*

@Entity
@Table(name = "biblioteca")
data class Biblioteca(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    var usuario: Usuario,

    @ManyToOne
    @JoinColumn(name = "id_videojuego", nullable = false)
    var videojuego: Videojuego,

    @Column(nullable = false)
    var fechaAdquisicion: String
)