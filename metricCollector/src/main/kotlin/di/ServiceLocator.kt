@file:Suppress("UNCHECKED_CAST")

package di

import org.koin.core.KoinComponent
import org.koin.core.inject
import services.CollaboratorService

object ServiceLocator : KoinComponent {

    val metricService by inject<MetricService>()
    val collaboratorService by inject<CollaboratorService>()
}