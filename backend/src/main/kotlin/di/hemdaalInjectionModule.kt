package di

import domains.UserService
import org.koin.dsl.module
import repositories.*
import utils.HashUtils

val hemdaalInjectionModule = module {
    single { UserService() }
    single { ProjectRepository() }
    single { OrganisationRepository() }
    single { UserRepository() }
    single { OrgCollaboratorRepository() }
    single { HashUtils() }
    single { CollaboratorRepository() }
}