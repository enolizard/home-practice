package by.enolizard.newsaggregator.api

import by.enolizard.newsaggregator.api.response.FeedsPage
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET(value = "v2/everything")
    fun getFeeds(
        @Query("q") q: String,
        @Query("apiKey") apiKey: String,
        @Query("page") page: Long,
        @Query("pageSize") pageSize: Int
    ): Single<FeedsPage>
}
