package com.example.androidtemplate

import android.app.Application
import android.content.Context
import com.crashlytics.android.Crashlytics
import com.google.android.gms.security.ProviderInstaller
import com.google.android.play.core.missingsplits.MissingSplitsManagerFactory
import com.google.firebase.FirebaseApp
import io.fabric.sdk.android.Fabric
import timber.log.Timber
import timber.log.Timber.DebugTree


class MainApplication: Application() {
    override fun onCreate() {
        if (MissingSplitsManagerFactory.create(this).disableAppIfMissingRequiredSplits()) {
            // Skip app initialization.
            return
        }
        super.onCreate()
        try {
            ProviderInstaller.installIfNeeded(this)
        } catch (ignored: Exception) {
        }
        appContext = this
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
        Fabric.with(this, Crashlytics())
        try {
            FirebaseApp.initializeApp(this)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    companion object {
        lateinit var appContext: Context
    }
}