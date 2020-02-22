package main.kotlin.models

import com.google.gson.annotations.SerializedName

data class TokenInfo(
    @SerializedName("token") val token: String
)