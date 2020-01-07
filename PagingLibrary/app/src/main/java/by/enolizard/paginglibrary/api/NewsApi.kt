package by.enolizard.paginglibrary.api

import by.enolizard.paginglibrary.api.response.FeedsPage
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET(value = "v2/everything")
    fun getFeeds(
        @Query("q") q: String,
        @Query("apiKey") apiKey: String,
        @Query("page") page: Long,
        @Query("pageSize") pageSize: Int
    ): Call<FeedsPage>
}
