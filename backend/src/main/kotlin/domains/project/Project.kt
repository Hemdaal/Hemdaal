package domains.project

import di.ServiceLocator
import domains.Collaborator
import domains.projectManagement.PMToolType
import domains.user.Access
import repositories.CollaboratorRepository
import repositories.ProjectCollaboratorRepository

class Project(
    val id: Long,
    val name: String
) {
    private val collaboratorRepository: CollaboratorRepository = ServiceLocator.collaboratorRepository
    private val projectCollaboratorRepository: ProjectCollaboratorRepository = ServiceLocator.orgAccessRepository
    private val softwareComponentRepository = ServiceLocator.softwareComponentRepository
    private val projectManagementRepository = ServiceLocator.projectManagementRepository

    fun getCollaborators(): Map<Collaborator, Access> {
        return projectCollaboratorRepository.getCollaboratorAccess(id)
    }

    fun getSoftwareComponents(): List<SoftwareComponent> {
        return softwareComponentRepository.getSoftwareComponentsBy(id)
    }

    fun getSoftwareComponent(softwareId: Long): SoftwareComponent? {
        return softwareComponentRepository.getSoftwareComponentsBy(id, softwareId)
    }

    fun createSoftwareComponent(name: String): SoftwareComponent {
        return softwareComponentRepository.createSoftwareComponent(name, id)
    }

    fun addCollaborator(name: String, email: String): Collaborator {
        val collaborator = collaboratorRepository.getOrCreateCollaborator(name, email)
        projectCollaboratorRepository.createCollaboratorAccess(collaborator.id, id, emptyList())
        return collaborator
    }

    fun getProjectManagement() = projectManagementRepository.getProjectManagement(id)

    fun setProjectManagement(
        pmToolType: PMToolType,
        toolUrl: String,
        token: String?
    ) = projectManagementRepository.addProjectManagement(
        projectId = id,
        url = toolUrl,
        type = pmToolType,
        token = token
    )
}