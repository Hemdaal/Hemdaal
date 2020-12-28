package utils.network

import io.ktor.client.*
import io.ktor.client.engine.okhttp.*

object NetworkEngine {

    val client = HttpClient(OkHttp) {
        engine {
            config { // this: OkHttpClient.Builder ->
                followRedirects(true)
            }
        }
    }
}
