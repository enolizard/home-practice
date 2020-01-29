package by.enolizard.paginglibrary.api.response

import com.squareup.moshi.Json

class FeedsPage(
    @field:Json(name = "status") val status: String,
    @field:Json(name = "totalResults") val totalCount: Int,
    @field:Json(name = "articles") val articles: List<Article>
)

class Article(
    @field:Json(name = "author") val author: String,
    @field:Json(name = "title") val title: String,
    @field:Json(name = "description") val description: String,
    @field:Json(name = "url") val url: String,
    @field:Json(name = "urlToImage") val urlToImage: String,
    @field:Json(name = "publishedAt") val date: String,
    @field:Json(name = "content") val content: String
//    , @field:Json(name= "source") val source: Source
)