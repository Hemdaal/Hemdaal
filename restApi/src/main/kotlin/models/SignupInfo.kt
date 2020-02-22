package main.kotlin.models

import com.google.gson.annotations.SerializedName

data class SignupInfo(
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String
)