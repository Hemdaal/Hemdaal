package main.kotlin

object ApplicationConfig {

    val DB_HOST = environment("DB_HOST", "localhost")
    val DB_PORT = environment("DB_PORT", "5432")
    val DB_USER_NAME = environment("DB_USERNAME", "hemdaal")
    val DB_PASSWORD = environment("DB_PASSWORD", "secrect")
    val AUTH_SECRET = environment("AUTH_SECRET_KEY", "secret")

    private fun environment(name: String, default: String): String {
        return System.getenv(name) ?: default
    }
}
