package main.kotlin

object ApplicationConfig {


    val DB_HOST = environment("DB_HOST", "127.0.0.1")
    val DB_PORT = environment("DB_PORT", "54321")
    val DB_USER_NAME = environment("DB_USERNAME", "user")
    val DB_PASSWORD = environment("DB_PASSWORD", "secret")
    val AUTH_SECRET = environment("_AUTH_SECRET_KEY", "secret")


    private fun environment(name: String, default: String): String {
        return System.getenv(name) ?: default
    }
}