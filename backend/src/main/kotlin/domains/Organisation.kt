package domains

import ServiceLocator
import repositories.OrgAccessRepository
import repositories.ProjectRepository
import repositories.UserRepository

data class Organisation(
    val id: Long,
    val name: String
) {

    private val projectRepository: ProjectRepository = ServiceLocator.get(ServiceLocator.PROJECT_REPOSITORY)
    private val userRepository: UserRepository = ServiceLocator.get(ServiceLocator.USER_REPOSITORY)
    private val orgAccessRepository: OrgAccessRepository = ServiceLocator.get(ServiceLocator.ORG_ACCESS_REPOSITORY)

    fun getProjects(): List<Project> {
        return projectRepository.getOrganisationProjects(id) ?: emptyList()
    }

    fun getUsers(): Map<User, List<Scope>> {
        val userIdScopeMap = orgAccessRepository.getUserAccess(id)
        val users = userRepository.getUserBy(userIdScopeMap.keys.toList())
        return users.associateWith { (userIdScopeMap[it.id] ?: emptyList()) }
    }
}