package com.calbert.blackkitegauntlet.presentation

import android.app.Application
import android.util.Log
import com.calbert.blackkitegauntlet.presentation.data.AppContainer
import com.calbert.blackkitegauntlet.presentation.data.AppDataContainer

class BlackKiteApplication: Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}