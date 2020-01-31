package by.enolizard.newsaggregator.api

import by.enolizard.newsaggregator.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val token: String = BuildConfig.API_TOKEN

        val original = chain.request()
        val request = original.newBuilder()
//            .header("Content-Type", "application/json")
//            .header("Accept", "application/json")
            .header("Authorization", "Bearer $token")
            .method(original.method, original.body)
            .build()

        return chain.proceed(request)
    }
}
