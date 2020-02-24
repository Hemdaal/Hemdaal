package domains

import ServiceLocator
import repositories.CollaboratorRepository
import repositories.OrgCollaboratorRepository
import repositories.ProjectRepository

class Organisation(
    val id: Long,
    val name: String
) {

    private val projectRepository: ProjectRepository = ServiceLocator.projectRepository
    private val collaboratorRepository: CollaboratorRepository = ServiceLocator.collaboratorRepository
    private val orgCollaboratorRepository: OrgCollaboratorRepository = ServiceLocator.orgAccessRepository

    fun getProjects(): List<Project> {
        return projectRepository.getOrganisationProjects(id) ?: emptyList()
    }

    fun getCollaborators(): Map<Collaborator, List<Scope>> {
        val colIdScopeMap = orgCollaboratorRepository.getCollaboratorAccess(id)
        val collaborators = collaboratorRepository.getCollaboratorBy(colIdScopeMap.keys.toList())
        return collaborators.associateWith { (colIdScopeMap[it.id] ?: emptyList()) }
    }

    fun createProject(name: String) {
        projectRepository.createProject(name, id)
    }

    fun addCollaborator(name: String, email: String): Collaborator {
        val collaborator = collaboratorRepository.getOrCreateCollaborator(name, email)
        orgCollaboratorRepository.createCollaboratorAccess(collaborator.id, id, emptyList())
        return collaborator
    }
}