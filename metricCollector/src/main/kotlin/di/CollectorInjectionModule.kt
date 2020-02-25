package di

import org.koin.dsl.module
import services.MetricService

val injectionModule = module {
    single { MetricService() }
}