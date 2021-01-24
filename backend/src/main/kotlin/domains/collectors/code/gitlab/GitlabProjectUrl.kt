package domains.collectors.code.gitlab

import io.ktor.http.*

class GitlabProjectUrl(urlString: String) {

    private val url = Url(urlString)

    fun getProjectPath(): String {
        val stringBuffer = StringBuffer(url.encodedPath)
        if (stringBuffer.first() == '/') {
            stringBuffer.deleteCharAt(0)
        }

        return stringBuffer.toString()
    }

    fun getBaseUrl(): String {
        return url.protocol.name + "://" + url.hostWithPort
    }
}
