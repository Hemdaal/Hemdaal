package services

import di.ServiceLocator
import domains.Collaborator
import repositories.CollaboratorRepository

class CollaboratorService {

    private val collaboratorRepository: CollaboratorRepository = ServiceLocator.collaboratorRepository

    fun getOrCreateCollaborator(name: String, email: String): Collaborator {
        return collaboratorRepository.getOrCreateCollaborator(name, email)
    }


    fun addCollaborators(nameEmailMap: Map<String, String>): Map<String, Collaborator> {
        val emailCollaboratorMap =
            collaboratorRepository.getCollaboratorByEmails(nameEmailMap.values.toList()).toMutableMap()
        val nonExistingNameEmailMap = nameEmailMap.filter {
            emailCollaboratorMap[it.value] == null
        }
        if (nonExistingNameEmailMap.isNotEmpty()) {
            emailCollaboratorMap.putAll(collaboratorRepository.addCollaborators(nonExistingNameEmailMap))
        }

        return emailCollaboratorMap
    }
}