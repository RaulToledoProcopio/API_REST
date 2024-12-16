package com.es.gameStore.model

import jakarta.persistence.*

@Entity
@Table(name = "biblioteca")
data class Biblioteca(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id_biblioteca: Long? = null,

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    var id_usuario: Usuario,

    @ManyToOne
    @JoinColumn(name = "id_videojuego", nullable = false)
    var id_videojuego: Videojuego,

    @Column(nullable = false)
    var fechaAdquisicion: String
)