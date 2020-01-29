package by.enolizard.paginglibrary

import android.app.Application
import by.enolizard.paginglibrary.di.AppComponent
import by.enolizard.paginglibrary.di.DaggerAppComponent

class App : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .application(this)
            .build()
    }
}
