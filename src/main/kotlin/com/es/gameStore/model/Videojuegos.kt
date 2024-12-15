package com.es.gameStore.model

import jakarta.persistence.*

@Entity
@Table(name = "videojuegos")
data class Videojuego(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false)
    var titulo: String,

    var descripcion: String? = null,

    var fechaLanzamiento: String? = null,

    var genero: String? = null,

    @Column(nullable = false)
    var precio: Double
)