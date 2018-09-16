package com.sudansh.player

import android.app.Application
import com.sudansh.player.di.appModule
import com.sudansh.player.di.localModule
import com.sudansh.player.di.remoteModule
import org.koin.android.ext.android.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(listOf(appModule, remoteModule, localModule))
    }

}