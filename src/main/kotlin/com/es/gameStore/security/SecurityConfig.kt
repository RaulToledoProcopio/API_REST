package com.es.gameStore.security

import com.nimbusds.jose.jwk.JWK
import com.nimbusds.jose.jwk.JWKSet
import com.nimbusds.jose.jwk.RSAKey
import com.nimbusds.jose.jwk.source.ImmutableJWKSet
import com.nimbusds.jose.jwk.source.JWKSource
import com.nimbusds.jose.proc.SecurityContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Autowired
    private lateinit var rsaKeys: RSAKeysProperties

    @Bean
    fun securityFilterChain(http: HttpSecurity) : SecurityFilterChain {

        return http
            .csrf { csrf -> csrf.disable() } // Cross-Site Forgery
            .authorizeHttpRequests { auth -> auth
                // Rutas públicas
                .requestMatchers("/usuarios/login", "/usuarios/register").permitAll() // Login y Registro
                .requestMatchers("/videojuegos").permitAll() // Listar videojuegos disponibles
                .requestMatchers("/videojuegos/{id}").permitAll() // Obtener videojuego por ID
                .requestMatchers("/biblioteca/{id_usuario}").permitAll() // Obtener biblioteca por usuario
                // Rutas protegidas
                .requestMatchers(HttpMethod.POST, "/videojuegos").hasRole("ADMIN") // Crear videojuego (solo admins)
                .requestMatchers(HttpMethod.POST, "/biblioteca").hasRole("ADMIN") // Agregar videojuego a biblioteca (solo admins)
                .requestMatchers(HttpMethod.DELETE, "/videojuegos/{id}").hasRole("ADMIN") // Eliminar videojuego (solo admins)
                .requestMatchers(HttpMethod.DELETE, "/biblioteca/{id_biblioteca}").hasRole("ADMIN") // Eliminar videojuego de biblioteca (solo admins)
                .requestMatchers(HttpMethod.PUT, "/videojuegos/{id}").hasRole("ADMIN") // Actualizar videojuego (solo admins)
                .requestMatchers(HttpMethod.PUT, "/usuarios/{id}").hasRole("ADMIN") // Actualizar usuario (solo admins)
                .requestMatchers(HttpMethod.DELETE, "/usuarios/{id}").hasRole("ADMIN") // Eliminar usuario (solo admins)
                .requestMatchers(HttpMethod.GET,"/usuarios").hasRole("ADMIN") // Listar todos los usuarios (solo admins)
                // Rutas privadas para usuarios autenticados
                .requestMatchers("/usuarios/{id}").authenticated() // Ver detalles de usuario
                .requestMatchers("/videojuegos").authenticated() // Ver lista de videojuegos (usuarios autenticados)
                .requestMatchers("/videojuegos/{id}").authenticated() // Ver detalles de un videojuego
                .requestMatchers("/biblioteca/{id_usuario}").authenticated() // Ver biblioteca de un usuario
                .requestMatchers(HttpMethod.DELETE, "/biblioteca/{id_biblioteca}").authenticated() // Eliminar videojuego de biblioteca
                .requestMatchers(HttpMethod.POST, "/biblioteca").authenticated() // Agregar videojuego a biblioteca
                .anyRequest().authenticated() // Cualquier otra ruta requiere autenticación
            }
            // Configuración de JWT
            .oauth2ResourceServer { oauth2 -> oauth2.jwt(Customizer.withDefaults()) }
            .sessionManagement { session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS) } // Stateless
            .httpBasic(Customizer.withDefaults()) // Basic Auth
            .build()
    }

    @Bean
    fun passwordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

    /**
     * Método que inicializa un objeto de tipo AuthenticationManager
     */
    @Bean
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration) : AuthenticationManager {
        return authenticationConfiguration.authenticationManager
    }


    /*
    MÉTODO PARA CODIFICAR UN JWT
     */
    @Bean
    fun jwtEncoder(): JwtEncoder {
        val jwk: JWK = RSAKey.Builder(rsaKeys.publicKey).privateKey(rsaKeys.privateKey).build()
        val jwks: JWKSource<SecurityContext> = ImmutableJWKSet(JWKSet(jwk))
        return NimbusJwtEncoder(jwks)
    }

    /*
    MÉTODO PARA DECODIFICAR UN JWT
     */
    @Bean
    fun jwtDecoder(): JwtDecoder {
        return NimbusJwtDecoder.withPublicKey(rsaKeys.publicKey).build()
    }

}