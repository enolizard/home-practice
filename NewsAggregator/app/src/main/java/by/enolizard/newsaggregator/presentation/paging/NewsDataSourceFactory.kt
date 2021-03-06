package by.enolizard.newsaggregator.presentation.paging

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import by.enolizard.newsaggregator.api.NewsApi
import by.enolizard.newsaggregator.api.response.Feed

class NewsDataSourceFactory(
    private val newsApi: NewsApi
) : DataSource.Factory<Int, Feed>() {

    private val _sourceLiveData = MutableLiveData<NewsDataSource>()

    val sourceLiveData: LiveData<NewsDataSource>
        get() = _sourceLiveData

    override fun create(): DataSource<Int, Feed> {
        val source = NewsDataSource(newsApi)
        _sourceLiveData.postValue(source)
        return source
    }
}
