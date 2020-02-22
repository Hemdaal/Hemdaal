package main.kotlin.auth

import com.auth0.jwk.JwkProvider
import com.auth0.jwk.JwkProviderBuilder
import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.interfaces.Claim
import com.auth0.jwt.interfaces.DecodedJWT
import domains.User
import main.kotlin.ApplicationConfig


class JWTTokenManager {

    private val signingAlgorithm: Algorithm = Algorithm.HMAC256(ApplicationConfig.AUTH_SECRET)
    private val issuer: String = "auth.hemdaal.com"
    private val emailKey: String = "email_key"

    fun createToken(user: User): String? {

        return JWT.create()
            .withIssuer(issuer)
            .withClaim(emailKey, user.email)
            .sign(signingAlgorithm)
    }

    fun getIssuer(): String {
        return issuer
    }

    fun getEmailFromJwt(claims: Map<String, Claim>): String? {
        return claims[emailKey]?.asString()
    }

    fun getJWKProvider(): JwkProvider {
        return JwkProviderBuilder(issuer).build()
    }

    fun verifyToken(token: String): String? {
        try {
            val verifier: JWTVerifier = JWT.require(signingAlgorithm)
                .withIssuer(issuer)
                .build()

            val jwt: DecodedJWT = verifier.verify(token)
            return jwt.getClaim(emailKey).asString()
        } catch (e: JWTVerificationException) {

        }

        return null
    }
}