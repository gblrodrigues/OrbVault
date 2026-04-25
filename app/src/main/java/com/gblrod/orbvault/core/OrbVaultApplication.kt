package com.gblrod.orbvault.core

import android.app.Application
import com.gblrod.orbvault.di.appModule
import com.gblrod.orbvault.di.storageModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext

class OrbVaultApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        GlobalContext.startKoin {
            androidLogger()
            androidContext(this@OrbVaultApplication)
            modules(
                appModule,
                storageModule
            )
        }
    }
}