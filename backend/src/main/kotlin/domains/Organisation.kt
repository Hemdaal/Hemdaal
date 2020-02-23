package domains

import ServiceLocator
import repositories.OrgAccessRepository
import repositories.ProjectRepository
import repositories.UserRepository

class Organisation(
    val id: Long,
    val name: String
) {

    private val projectRepository: ProjectRepository = ServiceLocator.projectRepository
    private val userRepository: UserRepository = ServiceLocator.userRepository
    private val orgAccessRepository: OrgAccessRepository = ServiceLocator.orgAccessRepository

    fun getProjects(): List<Project> {
        return projectRepository.getOrganisationProjects(id) ?: emptyList()
    }

    fun getUsers(): Map<User, List<Scope>> {
        val userIdScopeMap = orgAccessRepository.getUserAccess(id)
        val users = userRepository.getUserBy(userIdScopeMap.keys.toList())
        return users.associateWith { (userIdScopeMap[it.id] ?: emptyList()) }
    }
}