package di

import org.koin.dsl.module
import services.CollaboratorService
import services.MetricService

val injectionModule = module {
    single { MetricService() }
    single { CollaboratorService() }
}