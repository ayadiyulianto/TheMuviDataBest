package com.ayadiyulianto.themuvidatabest

import android.app.Application
import com.ayadiyulianto.themuvidatabest.core.di.databaseModule
import com.ayadiyulianto.themuvidatabest.core.di.networkModule
import com.ayadiyulianto.themuvidatabest.core.di.repositoryModule
import com.ayadiyulianto.themuvidatabest.di.useCaseModule
import com.ayadiyulianto.themuvidatabest.di.viewModelModule
import dagger.hilt.android.HiltAndroidApp
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

//@HiltAndroidApp
class MyApplication: Application()
{
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }
}