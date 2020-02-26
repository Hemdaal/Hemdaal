@file:Suppress("UNCHECKED_CAST")

import org.koin.core.KoinComponent
import org.koin.core.inject
import repositories.*
import utils.HashUtils

object ServiceLocator : KoinComponent {

    val metricRepository by inject<MetricRepository>()
    val projectRepository by inject<ProjectRepository>()
    val collaboratorRepository by inject<CollaboratorRepository>()
    val softwareComponentRepository by inject<SoftwareComponentRepository>()
    val userRepository by inject<UserRepository>()
    val orgAccessRepository by inject<ProjectCollaboratorRepository>()
    val hashUtils by inject<HashUtils>()
}