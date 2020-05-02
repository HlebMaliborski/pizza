package com.devlopersquad.papacodes

import android.app.Application
import com.devlopersquad.papacodes.common.di.navigationModule
import com.devlopersquad.papacodes.common.di.shareModule
import com.devlopersquad.papacodes.common.di.utilModule
import com.devlopersquad.papacodes.data.di.dataModule
import com.devlopersquad.papacodes.domain.di.domainModule
import com.devlopersquad.papacodes.main.di.mainModule
import com.devlopersquad.papacodes.presentation.di.presentationModule
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
            loadKoinModules(
                listOf(
                    utilModule,
                    presentationModule,
                    dataModule,
                    domainModule,
                    navigationModule,
                    shareModule,
                    mainModule
                )
            )
        }
    }
}