package main.kotlin.auth

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.interfaces.DecodedJWT
import domains.User
import main.kotlin.ApplicationConfig


object JWTTokenManager {

    private val SIGNING_ALGORITHM: Algorithm = Algorithm.HMAC256(ApplicationConfig.AUTH_SECRET)
    private const val ISSUER: String = "hemdaal_rest"
    private const val EMAIL_KEY: String = "email_key"

    fun createToken(user: User): String? {

        return JWT.create()
            .withIssuer(ISSUER)
            .withClaim(EMAIL_KEY, user.email)
            .sign(SIGNING_ALGORITHM)
    }

    fun verifyToken(token: String): String? {
        try {
            val verifier: JWTVerifier = JWT.require(SIGNING_ALGORITHM)
                .withIssuer(ISSUER)
                .build() //Reusable verifier instance

            val jwt: DecodedJWT = verifier.verify(token)
            return jwt.getClaim(EMAIL_KEY).asString()
        } catch (e: JWTVerificationException) {

        }

        return null
    }
}