package by.enolizard.paginglibrary.di.modules

import android.app.Application
import by.enolizard.paginglibrary.BuildConfig
import by.enolizard.paginglibrary.api.NewsApi
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class ApiModule {

    @Provides
    @Singleton
    fun provideCache(application: Application): Cache {
        val cacheSize: Long = 10 shl 20 // 10 MB
        val cacheFile = File(application.cacheDir, "cache")
        return Cache(cacheFile, cacheSize)
    }


    @Provides
    @Singleton
    fun provideClient(cache: Cache): OkHttpClient {
        val builder = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            builder.addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
        }

        return builder
            .cache(cache)
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(7, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideNewsApi(client: OkHttpClient): NewsApi {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .build()
            .create(NewsApi::class.java)
    }
}
