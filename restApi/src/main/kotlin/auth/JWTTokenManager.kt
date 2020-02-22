package main.kotlin.auth

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.interfaces.Claim
import com.auth0.jwt.interfaces.DecodedJWT
import domains.User
import main.kotlin.ApplicationConfig
import java.util.*


class JWTTokenManager {

    private val signingAlgorithm: Algorithm = Algorithm.HMAC256(ApplicationConfig.AUTH_SECRET)
    private val issuer: String = "auth.hemdaal.com"
    private val validityInMs = 36_000_00 * 10 // 10 hours
    private val emailKey: String = "email_key"

    val verifier: JWTVerifier = JWT
        .require(signingAlgorithm)
        .withIssuer(issuer)
        .build()

    fun createToken(user: User): String? {

        return JWT.create()
            .withSubject("Authentication")
            .withIssuer(issuer)
            .withClaim(emailKey, user.email)
            .withExpiresAt(getExpiration())
            .sign(signingAlgorithm)
    }

    fun getEmailFromJwt(claims: Map<String, Claim>): String? {
        return claims[emailKey]?.asString()
    }

    fun verifyToken(token: String): String? {
        try {
            val jwt: DecodedJWT = verifier.verify(token)
            return jwt.getClaim(emailKey).asString()
        } catch (e: JWTVerificationException) {

        }

        return null
    }

    private fun getExpiration() = Date(System.currentTimeMillis() + validityInMs)
}