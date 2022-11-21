package com.melnikvlad.pokemonius

import android.app.Application
import com.melnikvlad.pokemonius.di.AppComponent
import com.melnikvlad.pokemonius.di.DaggerAppComponent

class PokeApp: Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent
            .factory()
            .create(this)
    }
}