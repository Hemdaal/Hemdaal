package main.kotlin

object ApplicationConfig {

    init {
        environment("DB_HOST", "127.0.0.1")
        environment("DB_PORT", "54321")
        environment("DB_USERNAME", "user")
        environment("DB_PASSWORD", "secret")
    }

    private fun environment(name: String, default: String): String {
        return System.getenv(name) ?: default
    }
}