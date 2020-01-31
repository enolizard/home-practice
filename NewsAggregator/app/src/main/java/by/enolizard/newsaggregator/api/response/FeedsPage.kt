package by.enolizard.newsaggregator.api.response

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
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Article

        if (author != other.author) return false
        if (title != other.title) return false
        if (description != other.description) return false
        if (url != other.url) return false
        if (urlToImage != other.urlToImage) return false
        if (date != other.date) return false
        if (content != other.content) return false

        return true
    }

    override fun hashCode(): Int {
        var result = author.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + url.hashCode()
        result = 31 * result + urlToImage.hashCode()
        result = 31 * result + date.hashCode()
        result = 31 * result + content.hashCode()
        return result
    }
}