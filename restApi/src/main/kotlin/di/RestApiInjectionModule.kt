package main.kotlin.di

import main.kotlin.auth.JWTTokenManager
import org.koin.dsl.module

val injectionModule = module {
    single { JWTTokenManager() }
}
