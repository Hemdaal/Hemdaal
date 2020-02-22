@file:Suppress("UNCHECKED_CAST")

import org.koin.core.KoinComponent
import org.koin.core.inject
import repositories.OrgAccessRepository
import repositories.OrganisationRepository
import repositories.ProjectRepository
import repositories.UserRepository
import utils.HashUtils

object ServiceLocator : KoinComponent {

    val projectRepository by inject<ProjectRepository>()
    val organisationRepository by inject<OrganisationRepository>()
    val userRepository by inject<UserRepository>()
    val orgAccessRepository by inject<OrgAccessRepository>()
    val hashUtils by inject<HashUtils>()
}