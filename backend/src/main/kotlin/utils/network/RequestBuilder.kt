package utils.network

import com.google.gson.Gson
import okhttp3.Headers.Companion.toHeaders
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

class RequestBuilder(
    baseUrl: String
) {

    private val urlBuffer = StringBuffer(baseUrl)
    private val headers = mutableMapOf<String, String>()
    private val params = mutableMapOf<String, String>()
    private var requestType: RequestType = RequestType.GET
    private var body: String = ""

    fun appendPath(pathToAppend: String): RequestBuilder {
        if (urlBuffer[urlBuffer.length - 1] == '/' || pathToAppend[0] == '/') {
            urlBuffer.append(pathToAppend)
        } else {
            urlBuffer.append("/").append(pathToAppend)
        }
        return this
    }

    fun appendPath(pathToAppend: Int) = appendPath(pathToAppend.toString())

    fun addHeader(key: String, value: String): RequestBuilder {
        headers[key] = value
        return this
    }

    fun addHeader(key: String, value: Int) = addHeader(key, value.toString())

    fun addHeader(key: String, value: Long) = addHeader(key, value.toString())

    fun addParams(key: String, value: String): RequestBuilder {
        params[key] = value
        return this
    }

    fun addParams(key: String, value: Int) = addParams(key, value.toString())

    fun addParams(key: String, value: Long) = addParams(key, value.toString())

    fun addPayload(graphQLRequest: GraphQLRequest): RequestBuilder {
        body = Gson().toJson(graphQLRequest)
        requestType = RequestType.POST
        return this
    }

    fun build(): Request {
        val requestBuilder = Request.Builder()
            .url(buildUrl(urlBuffer.toString(), params))
            .headers(headers.toHeaders())

        when (requestType) {
            RequestType.GET -> requestBuilder.get()
            RequestType.POST -> requestBuilder.post(body.toRequestBody())
        }

        return requestBuilder.build()
    }

    private fun buildUrl(url: String, params: Map<String, String>): String {
        var isFirstParam = !url.contains("?")

        val stringBuffer = StringBuffer()
        stringBuffer.append(url)

        params.forEach { (key, value) ->
            if (isFirstParam) {
                stringBuffer.append("?").append(key).append("=").append(value)
                isFirstParam = false
            } else {
                stringBuffer.append("&").append(key).append("=").append(value)
            }
        }

        return stringBuffer.toString()
    }
}

enum class RequestType {
    GET,
    POST
}
