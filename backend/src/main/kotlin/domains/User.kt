package domains

import di.ServiceLocator
import repositories.CollaboratorRepository
import repositories.ProjectCollaboratorRepository
import repositories.ProjectRepository

class User(
    val id: Long,
    val name: String,
    val email: String
) {
    private val projectCollaboratorRepository: ProjectCollaboratorRepository = ServiceLocator.orgAccessRepository
    private val projectRepository: ProjectRepository = ServiceLocator.projectRepository
    private val collaboratorRepository: CollaboratorRepository = ServiceLocator.collaboratorRepository

    fun getProjects(): Map<Project, List<Scope>> {
        return projectCollaboratorRepository.getProjectsWithScope(getCollaborator().id)
    }

    private fun getCollaborator(): Collaborator {
        return collaboratorRepository.getOrCreateCollaborator(name, email)
    }

    fun createProject(name: String): Pair<Project, List<Scope>> {
        return projectRepository.createProject(name).let {
            projectCollaboratorRepository.createCollaboratorAccess(
                collaboratorId = getCollaborator().id,
                projectId = it.id,
                scopes = emptyList()
            )
            Pair(it, emptyList())
        }
    }
}