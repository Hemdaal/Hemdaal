package di

import org.koin.dsl.module
import services.CollaboratorService

val injectionModule = module {
    single { MetricService() }
    single { CollaboratorService() }
}