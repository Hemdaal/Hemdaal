@file:Suppress("UNCHECKED_CAST")

import org.koin.core.KoinComponent
import org.koin.core.inject
import repositories.*
import utils.HashUtils

object ServiceLocator : KoinComponent {

    val projectRepository by inject<ProjectRepository>()
    val organisationRepository by inject<OrganisationRepository>()
    val collaboratorRepository by inject<CollaboratorRepository>()
    val userRepository by inject<UserRepository>()
    val orgAccessRepository by inject<OrgCollaboratorRepository>()
    val hashUtils by inject<HashUtils>()
}