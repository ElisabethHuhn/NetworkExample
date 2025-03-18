package org.elisabethhuhn.networkexample.di


import org.elisabethhuhn.networkexample.core.data.HttpClientFactory
import org.elisabethhuhn.networkexample.log.data.network.RemoteLogDataSource
import org.elisabethhuhn.networkexample.log.data.network.KtorRemoteLogDataSource
import org.elisabethhuhn.networkexample.log.domain.LogRepository
import org.elisabethhuhn.networkexample.log.data.repository.DefaultLogRepository
import org.elisabethhuhn.networkexample.log.presentation.LoggerViewModel

import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf

import org.koin.core.module.Module

import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformModule: Module

val sharedModule = module {
    single { HttpClientFactory.create(get()) }
    singleOf(::KtorRemoteLogDataSource).bind<RemoteLogDataSource>()
    singleOf(::DefaultLogRepository).bind<LogRepository>()

    viewModelOf(::LoggerViewModel)
}