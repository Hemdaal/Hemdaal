package domains

import ServiceLocator
import repositories.OrganisationRepository
import repositories.UserRepository

class HemdaalService {

    private val organisationRepository: OrganisationRepository = ServiceLocator.get(ServiceLocator.ORG_REPOSITORY)
    private val userRepository: UserRepository = ServiceLocator.get(ServiceLocator.USER_REPOSITORY)

    fun getOrganisationBy(id: Long): Organisation? {
        return organisationRepository.getOrganisationBy(id)
    }

    fun getOrganisationBy(name: String): Organisation? {
        return organisationRepository.getOrganisationBy(name)
    }

    fun createOrganisation(name: String, user: User) {

    }

    fun createUser(name: String, email: String) {

    }

    fun getUserBy(email: String) {

    }
}