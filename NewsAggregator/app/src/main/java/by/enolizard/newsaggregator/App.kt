package by.enolizard.newsaggregator

import android.app.Application
import by.enolizard.newsaggregator.di.AppComponent
import by.enolizard.newsaggregator.di.DaggerAppComponent

class App : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .application(this)
            .build()
    }
}
