package di

import org.koin.dsl.module
import repositories.*
import services.UserService
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
    single { PMToolRepository() }
    single { GITToolRepository() }
    single { BuildToolRepository() }
}