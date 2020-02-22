package main.kotlin.models

import com.google.gson.annotations.SerializedName

data class LoginInfo(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String
)