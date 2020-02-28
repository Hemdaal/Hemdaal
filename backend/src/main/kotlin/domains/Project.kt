package domains

import di.ServiceLocator
import repositories.CollaboratorRepository
import repositories.ProjectCollaboratorRepository

class Project(
    val id: Long,
    val name: String
) {
    private val collaboratorRepository: CollaboratorRepository = ServiceLocator.collaboratorRepository
    private val projectCollaboratorRepository: ProjectCollaboratorRepository = ServiceLocator.orgAccessRepository
    private val softwareComponentRepository = ServiceLocator.softwareComponentRepository

    fun getCollaborators(): Map<Collaborator, List<Scope>> {
        return projectCollaboratorRepository.getCollaboratorAccess(id)
    }

    fun getSoftwareComponents(): List<SoftwareComponent> {
        return softwareComponentRepository.getSoftwareComponentsBy(id)
    }

    fun createSoftwareComponent(name: String): SoftwareComponent {
        return softwareComponentRepository.createSoftwareComponent(name, id)
    }

    fun addCollaborator(name: String, email: String): Collaborator {
        val collaborator = collaboratorRepository.getOrCreateCollaborator(name, email)
        projectCollaboratorRepository.createCollaboratorAccess(collaborator.id, id, emptyList())
        return collaborator
    }
}