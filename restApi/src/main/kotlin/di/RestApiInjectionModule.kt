package main.kotlin.di

import main.kotlin.auth.JWTTokenManager
import main.kotlin.graphql.AuthenticatedUserQuery
import main.kotlin.graphql.UserCreationQuery
import org.koin.dsl.module

val injectionModule = module {
    single { JWTTokenManager() }
    single { AuthenticatedUserQuery(get(), get()) }
    single { UserCreationQuery(get(), get()) }
}