package domains

import ServiceLocator
import repositories.CollaboratorRepository
import repositories.OrgCollaboratorRepository
import repositories.OrganisationRepository

class User(
    val id: Long,
    val name: String,
    val email: String
) {
    private val orgCollaboratorRepository: OrgCollaboratorRepository = ServiceLocator.orgAccessRepository
    private val organisationRepository: OrganisationRepository = ServiceLocator.organisationRepository
    private val collaboratorRepository: CollaboratorRepository = ServiceLocator.collaboratorRepository

    fun getOrganisations(): List<Organisation> {
        val orgIds = orgCollaboratorRepository.getOrganisations(getCollaborator().id)
        return organisationRepository.getOrganisationBy(orgIds)
    }

    fun getCollaborator(): Collaborator {
        return collaboratorRepository.getOrCreateCollaborator(name, email)
    }

    fun createOrganisation(name: String): Organisation? {
        val organisation = organisationRepository.createOrganisation(name)
        organisation?.let {
            orgCollaboratorRepository.createCollaboratorAccess(
                colId = getCollaborator().id,
                orgId = organisation.id,
                scopes = emptyList()
            )
        }

        return organisation
    }
}