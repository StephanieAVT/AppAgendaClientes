package com.example.crudmaxprocess

import android.app.Application
import com.example.crudmaxprocess.di.appModule
import com.example.crudmaxprocess.di.storageModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CalendarApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@CalendarApplication)
            modules(appModule)
            modules(storageModule)

        }
    }
}