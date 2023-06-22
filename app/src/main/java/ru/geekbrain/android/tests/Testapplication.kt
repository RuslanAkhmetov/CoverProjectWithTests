package ru.geekbrain.android.tests

import android.app.Application
import org.koin.core.context.startKoin
import ru.geekbrain.android.tests.di.application

class TestApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin{
            modules(application)
        }
    }

}