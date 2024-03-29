package com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge

import android.app.Application
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.source.local.database.AppDatabase

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        AppDatabase.getDatabase(this)
    }
}