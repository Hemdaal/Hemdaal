package di

import domains.System
import org.koin.dsl.module
import repositories.*
import utils.HashUtils

val hemdaalInjectionModule = module {
    single { System() }
    single { ProjectRepository() }
    single { UserRepository() }
    single { ProjectCollaboratorRepository() }
    single { HashUtils() }
    single { CollaboratorRepository() }
    single { SoftwareComponentRepository() }
    single { CommitRepository() }
    single { CodeManagementRepository() }
    single { UserProjectDashboardRepository() }
    single { ProjectWidgetRepository() }
}
