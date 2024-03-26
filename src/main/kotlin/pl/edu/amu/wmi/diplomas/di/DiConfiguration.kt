package pl.edu.amu.wmi.diplomas.di

import io.ktor.server.application.*
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import pl.edu.amu.wmi.diplomas.service.DiplomaGetService
import pl.edu.amu.wmi.diplomas.service.DiplomaUpdateService

fun Application.configureDi() {
    install(Koin) {
        modules(serviceModule)
    }
}

private val serviceModule = module {
    single<DiplomaUpdateService> { DiplomaUpdateService() }
    single<DiplomaGetService> { DiplomaGetService() }
}