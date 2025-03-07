package org.elisabethhuhn.networkexample.di

import org.elisabethhuhn.networkexample.di.platformModule
import org.elisabethhuhn.networkexample.di.sharedModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(sharedModule, platformModule)
    }
}