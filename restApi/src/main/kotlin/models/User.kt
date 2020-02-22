package main.kotlin.models

import com.google.gson.annotations.SerializedName


data class User(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String
) {
    constructor(user: domains.User) : this(user.id, user.name, user.email)
}