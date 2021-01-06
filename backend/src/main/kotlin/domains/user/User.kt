package domains.user

import di.ServiceLocator
import domains.Collaborator
import domains.project.Project
import domains.widgets.UserProjectDashboard
import repositories.CollaboratorRepository
import repositories.ProjectCollaboratorRepository
import repositories.ProjectRepository
import repositories.UserProjectDashboardRepository

class User(
    val id: Long,
    val name: String,
    val email: String
) {
    private val projectCollaboratorRepository: ProjectCollaboratorRepository = ServiceLocator.orgAccessRepository
    private val projectRepository: ProjectRepository = ServiceLocator.projectRepository
    private val collaboratorRepository: CollaboratorRepository = ServiceLocator.collaboratorRepository
    private val userProjectRepository: UserProjectDashboardRepository = ServiceLocator.userProjectDashboardRepository

    fun getProjects(): Map<Project, Access> {
        return projectCollaboratorRepository.getProjectsWithScope(getCollaborator().id)
    }

    fun getProject(projectId: Long): Pair<Project, Access>? {
        return projectCollaboratorRepository.getProjectWithScope(getCollaborator().id, projectId)
    }

    private fun getCollaborator(): Collaborator {
        return collaboratorRepository.getOrCreateCollaborator(name, email)
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
}
