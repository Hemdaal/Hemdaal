package domains

import di.ServiceLocator
import domains.user.Collaborator
import domains.user.User
import repositories.CollaboratorRepository
import repositories.UserRepository
import utils.HashUtils

class UserService {
    private val userRepository: UserRepository = ServiceLocator.userRepository
    private val collaboratorRepository: CollaboratorRepository = ServiceLocator.collaboratorRepository
    private val hashUtils: HashUtils = ServiceLocator.hashUtils

    fun createUser(name: String, email: String, password: String): Boolean {
        if (userRepository.isEmailExist(email)) {
            return false
        }

        val passwordHash = hashUtils.sha256(password)
        userRepository.createUser(name, email, passwordHash)

        return true
    }

    fun getUserBy(email: String, password: String): User? {
        val passwordHash = hashUtils.sha256(password)
        return userRepository.returnUserByCheckingEmailAndPassword(email, passwordHash)
    }

    fun getUserBy(email: String): User? {
        return userRepository.returnUserByEmail(email)
    }

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
