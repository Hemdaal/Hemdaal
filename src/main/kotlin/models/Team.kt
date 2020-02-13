package models

class Team(
    var name : String
) {
    private var collaborators : MutableSet<Collaborator> = mutableSetOf()

    fun addCollaborators(collaborator: Collaborator) {
        collaborators.add(collaborator)
    }

    fun getCollaborators() = collaborators.toList()
}