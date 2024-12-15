package com.es.gameStore.repository

import com.es.gameStore.model.Videojuego
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface VideojuegoRepository : JpaRepository<Videojuego, Long> {
}
