package com.worldline.pointsofinterest

import android.app.Application
import io.realm.Realm

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialize realm and data.
        Realm.init(this)
    }
}