@file:Suppress("UNCHECKED_CAST")

package di

import org.koin.core.KoinComponent
import org.koin.core.inject
import services.MetricService

object ServiceLocator : KoinComponent {

    val metricService by inject<MetricService>()
}