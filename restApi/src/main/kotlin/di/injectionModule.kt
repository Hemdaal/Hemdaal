package main.kotlin.di

import main.kotlin.auth.JWTTokenManager
import main.kotlin.models.AuthenticatedUserQuery
import main.kotlin.models.UserCreationQuery
import org.koin.dsl.module

val injectionModule = module {
    single { JWTTokenManager() }
    single { AuthenticatedUserQuery(get()) }
    single { UserCreationQuery(get(), get()) }
}