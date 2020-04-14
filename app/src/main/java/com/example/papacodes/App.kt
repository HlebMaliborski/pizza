package com.example.papacodes

import android.app.Application
import com.example.papacodes.common.di.utilModule
import com.example.papacodes.data.di.dataModule
import com.example.papacodes.domain.di.domainModule
import com.example.papacodes.presentation.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@App)
            loadKoinModules(listOf(utilModule, presentationModule, dataModule, domainModule))
        }
    }
}