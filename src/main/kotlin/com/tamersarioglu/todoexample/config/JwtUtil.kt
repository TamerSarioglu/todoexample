package com.tamersarioglu.todoexample.config

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.Date
import javax.crypto.SecretKey

@Service
class JwtUtil {

    @Value("\${jwt.secret}")
    private lateinit var jwtSecret: String

    @Value("\${jwt.expiration}")
    private var jwtExpiration: Long = 86400000

    fun generateToken(userDetails: UserDetails): String {
        return Jwts.builder()
            .subject(userDetails.username)
            .issuedAt(Date(System.currentTimeMillis()))
            .expiration(Date(System.currentTimeMillis() + jwtExpiration))
            .signWith(getSignInKey(), Jwts.SIG.HS256)
            .compact()
    }

    fun validateToken(token: String, userDetails: UserDetails): Boolean {
        val username = extractUsername(token)
        return (username == userDetails.username && !isTokenExpired(token))
    }

    fun extractUsername(token: String): String {
        return extractAllClaims(token).subject
    }

    private fun isTokenExpired(token: String): Boolean {
        return extractAllClaims(token).expiration.before(Date())
    }

    private fun extractAllClaims(token: String): Claims {
        val jws = Jwts.parser()
            .verifyWith(getSignInKey())
            .build()
            .parseSignedClaims(token)
        return jws.payload
    }

    private fun getSignInKey(): SecretKey {
        val bytes = Decoders.BASE64.decode(jwtSecret)
        require(bytes.size >= 32) { "JWT secret must be at least 256 bits (32 bytes) after Base64 decode." }
        return Keys.hmacShaKeyFor(bytes)
    }
}