package domains

import ServiceLocator
import repositories.OrganisationRepository

class DashboardService {

    private val organisationRepository: OrganisationRepository = ServiceLocator.get(ServiceLocator.ORG_REPOSITORY)

    fun getOrganisationBy(id: Long): Organisation? {
        return organisationRepository.getOrganisationBy(id)
    }

    fun getOrganisationBy(name: String): Organisation? {
        return organisationRepository.getOrganisationBy(name)
    }

    fun createOrganisation(name: String, user: User) {

    }
}