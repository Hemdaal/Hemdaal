package main.kotlin.auth

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.interfaces.DecodedJWT
import domains.User
import main.kotlin.ApplicationConfig


class JWTTokenManager {

    private val signingAlgorithm: Algorithm = Algorithm.HMAC256(ApplicationConfig.AUTH_SECRET)
    private val issuer: String = "hemdaal_rest"
    private val emailKey: String = "email_key"

    fun createToken(user: User): String? {

        return JWT.create()
            .withIssuer(issuer)
            .withClaim(emailKey, user.email)
            .sign(signingAlgorithm)
    }

    fun verifyToken(token: String): String? {
        try {
            val verifier: JWTVerifier = JWT.require(signingAlgorithm)
                .withIssuer(issuer)
                .build() //Reusable verifier instance

            val jwt: DecodedJWT = verifier.verify(token)
            return jwt.getClaim(emailKey).asString()
        } catch (e: JWTVerificationException) {

        }

        return null
    }
}