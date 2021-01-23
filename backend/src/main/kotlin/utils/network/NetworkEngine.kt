package utils.network

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import java.lang.reflect.Type


object NetworkEngine {
    private val client = OkHttpClient()

    fun <T> execute(request: Request, valueType: Type): T? {
        val response = client.newCall(request).execute()

        try {
            return Gson().fromJson(response.body?.string(), valueType)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }

    fun <T> execute(request: Request, valueType: Class<T>): T? {
        val response = client.newCall(request).execute()

        try {
            return Gson().fromJson(response.body?.string(), valueType)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }


}
