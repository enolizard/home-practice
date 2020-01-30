package by.enolizard.newsaggregator.di

import android.app.Application
import by.enolizard.newsaggregator.presentation.activities.FeedActivity
import by.enolizard.newsaggregator.di.modules.ApiModule
import by.enolizard.newsaggregator.di.modules.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton


@Component(
    modules = [
        ApiModule::class,
        ViewModelModule::class
    ]
)
@Singleton
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(app: Application): Builder

        fun build(): AppComponent
    }

    fun inject(feedActivity: FeedActivity)
}