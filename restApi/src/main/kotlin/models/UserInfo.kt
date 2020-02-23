package main.kotlin.models

import com.expedia.graphql.annotations.GraphQLContext
import com.google.gson.annotations.SerializedName
import domains.User
import io.ktor.auth.UserIdPrincipal
import io.ktor.auth.principal
import main.kotlin.graphql.GraphQLCallContext


data class UserInfo(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String
) {
    constructor(user: User) : this(user.id, user.name, user.email)

    fun organisations(@GraphQLContext context: GraphQLCallContext): List<OrganisationInfo> {
        return User(id, name, email).getOrganisations().map {
            OrganisationInfo(it)
        }
    }

    fun createOrganisation(@GraphQLContext context: GraphQLCallContext, name: String): OrganisationInfo? {
        val email = context.call.principal<UserIdPrincipal>()?.name

        if (email != null) {
            return User(id, name, email).createOrganisation(name)?.let {
                OrganisationInfo(it)
            }
        }

        return null
    }
}