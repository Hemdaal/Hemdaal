package network

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp

object NetworkEngine {

    val client = HttpClient(OkHttp) {
        engine {
            config { // this: OkHttpClient.Builder ->
                followRedirects(true)
            }

        }
    }
}