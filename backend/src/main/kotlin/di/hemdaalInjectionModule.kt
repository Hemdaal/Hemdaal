package di

import domains.UserService
import org.koin.dsl.module
import repositories.*
import utils.HashUtils

val hemdaalInjectionModule = module {
    single { UserService() }
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
