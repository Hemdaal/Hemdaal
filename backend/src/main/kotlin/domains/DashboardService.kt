package domains

import ServiceLocator
import repositories.OrganisationRepository

class DashboardService {
    private val organisationRepository: OrganisationRepository = ServiceLocator.organisationRepository

    fun getOrganisationBy(id: Long): Organisation? {
        return organisationRepository.getOrganisationBy(id)
    }

    fun createOrganisation(name: String, user: User) {

    }
}