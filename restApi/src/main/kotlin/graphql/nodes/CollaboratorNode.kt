package graphql.nodes

import domains.user.Collaborator

data class CollaboratorNode(
    val id: Long,
    val name: String,
    val email: String
) {
    constructor(collaborator: Collaborator) : this(collaborator.id, collaborator.name, collaborator.email)
}
