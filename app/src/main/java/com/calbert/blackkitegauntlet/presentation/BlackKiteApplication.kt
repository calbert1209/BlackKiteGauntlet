package com.calbert.blackkitegauntlet.presentation

import android.app.Application
import android.util.Log

class BlackKiteApplication: Application() {
    lateinit var container: String

    override fun onCreate() {
        super.onCreate()
        container = "black kite"
        Log.i("on create", this.container);
    }
}