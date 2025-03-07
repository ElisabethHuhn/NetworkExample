package org.elisabethhuhn.networkexample.application

import android.app.Application
import org.elisabethhuhn.networkexample.di.initKoin

import org.koin.android.ext.koin.androidContext

class NetworkExampleApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@NetworkExampleApplication)
        }
    }
}