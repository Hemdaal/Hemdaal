package domains.user

import di.ServiceLocator
import domains.dashboard.UserProjectDashboard
import domains.project.Project
import domains.project.SoftwareComponent
import repositories.*

class User(
    val id: Long,
    val name: String,
    val email: String
) {
    private val projectCollaboratorRepository: ProjectCollaboratorRepository = ServiceLocator.orgAccessRepository
    private val projectRepository: ProjectRepository = ServiceLocator.projectRepository
    private val collaboratorRepository: CollaboratorRepository = ServiceLocator.collaboratorRepository
    private val userProjectRepository: UserProjectDashboardRepository = ServiceLocator.userProjectDashboardRepository
    private val softwareComponentRepository: SoftwareComponentRepository = ServiceLocator.softwareComponentRepository

    fun getProjects(): Map<Project, Access> {
        return projectCollaboratorRepository.getProjectsWithScope(getCollaborator().id)
    }

    fun getProject(projectId: Long): Pair<Project, Access>? {
        return projectCollaboratorRepository.getProjectWithScope(getCollaborator().id, projectId)
    }

    fun createProject(name: String): Pair<Project, Access> {
        return projectRepository.createProject(name).let {
            projectCollaboratorRepository.createCollaboratorAccess(
                collaboratorId = getCollaborator().id,
                projectId = it.id,
                scopes = emptyList()
            )
            Pair(it, Access.createOwnerAccess())
        }
    }

    fun getProjectDashboard(projectId: Long): UserProjectDashboard {
        return userProjectRepository.getUserProjectDashboard(userId = id, projectId = projectId)
            ?: userProjectRepository.createUserProjectDashboard(userId = id, projectId = projectId)
    }

    fun getSoftwareComponent(softwareId: Long): SoftwareComponent? {
        return softwareComponentRepository.getSoftwareComponentsByUser(userId = id, softwareId = softwareId)
    }

    private fun getCollaborator(): Collaborator {
        return collaboratorRepository.getOrCreateCollaborator(name, email)
    }
}
