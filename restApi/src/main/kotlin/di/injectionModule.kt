package main.kotlin.di

import main.kotlin.auth.JWTTokenManager
import main.kotlin.models.UserQuery
import org.koin.dsl.module

val injectionModule = module {
    single { JWTTokenManager() }
    single { UserQuery(get()) }
}