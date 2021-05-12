package graphql.nodes

import com.google.gson.annotations.SerializedName

data class LoginNode(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String
)
