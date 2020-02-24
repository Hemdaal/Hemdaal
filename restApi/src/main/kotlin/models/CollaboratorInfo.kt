package main.kotlin.models

import domains.Collaborator

data class CollaboratorInfo(
    val id: Long,
    val name: String,
    val email: String
) {
    constructor(collaborator: Collaborator) : this(collaborator.id, collaborator.name, collaborator.email)
}