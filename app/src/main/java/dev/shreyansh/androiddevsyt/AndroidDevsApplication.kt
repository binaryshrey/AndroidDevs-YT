package dev.shreyansh.androiddevsyt

import android.app.Application
import timber.log.Timber

class AndroidDevsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}