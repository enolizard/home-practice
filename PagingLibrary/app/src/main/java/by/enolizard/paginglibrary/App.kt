package by.enolizard.paginglibrary

import android.app.Application
import android.content.Context
import by.enolizard.paginglibrary.api.NewsApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        init(appContext = this.applicationContext)
    }

    companion object {
        val api: NewsApi get() = _api

        private lateinit var _api: NewsApi
        private fun init(appContext: Context) {
            val client = OkHttpClient.Builder()
                .addInterceptor(
                    HttpLoggingInterceptor().apply {
                        this.level = HttpLoggingInterceptor.Level.BODY
                    }
                ).build()

            _api = Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create())
                .baseUrl(BuildConfig.BASE_URL)
                .client(client)
                .build()
                .create(NewsApi::class.java)
        }
    }
}