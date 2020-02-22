package di

import domains.UserService
import org.koin.dsl.module
import repositories.OrgAccessRepository
import repositories.OrganisationRepository
import repositories.ProjectRepository
import repositories.UserRepository
import utils.HashUtils

val hemdaalInjectionModule = module {
    single { UserService() }
    single { ProjectRepository() }
    single { OrganisationRepository() }
    single { UserRepository() }
    single { OrgAccessRepository() }
    single { HashUtils() }
}