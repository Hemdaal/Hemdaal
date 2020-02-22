package main.kotlin.di

import main.kotlin.auth.JWTTokenManager
import main.kotlin.graphql.queries.UserQuery
import org.koin.dsl.module

val injectionModule = module {
    single { JWTTokenManager() }
    single { UserQuery(get()) }
}