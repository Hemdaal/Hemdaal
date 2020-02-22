package main.kotlin.models


data class User(
    val id: Long,
    val name: String,
    val email: String
) {
    constructor(user: domains.User) : this(user.id, user.name, user.email)
}