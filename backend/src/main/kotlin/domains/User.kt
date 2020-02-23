package domains

import ServiceLocator
import repositories.OrgAccessRepository
import repositories.OrganisationRepository

class User(
    val id: Long,
    val name: String,
    val email: String
) {
    private val orgAccessRepository: OrgAccessRepository = ServiceLocator.orgAccessRepository
    private val organisationRepository: OrganisationRepository = ServiceLocator.organisationRepository

    fun getOrganisations(): List<Organisation> {
        val orgIds = orgAccessRepository.getOrganisations(userId = id)
        return organisationRepository.getOrganisationBy(orgIds)
    }
}