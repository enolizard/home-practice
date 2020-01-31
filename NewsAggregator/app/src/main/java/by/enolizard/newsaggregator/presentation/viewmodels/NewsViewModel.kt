package by.enolizard.newsaggregator.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import androidx.paging.toLiveData
import by.enolizard.newsaggregator.api.NewsApi
import by.enolizard.newsaggregator.api.response.Article
import by.enolizard.newsaggregator.presentation.paging.NewsDataSourceFactory
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class NewsViewModel @Inject constructor(
    private val api: NewsApi
) : ViewModel() {

    private val _feeds = MutableLiveData<List<Article>>()
    val feeds: LiveData<List<Article>> get() = _feeds

    private fun loadNews() {
        val sourceFactory = NewsDataSourceFactory(api)

        val pagedListConfig = PagedList.Config.Builder()
            .setPageSize(20)
            .setPrefetchDistance(20)
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(20 * 3)
            .setMaxSize(PagedList.Config.MAX_SIZE_UNBOUNDED)
            .build()

        val livePagedList = sourceFactory.toLiveData(pagedListConfig)


        val listing =
            Listing(
                pagedList = livePagedList,
                state = switchMap(sourceFactory.sourceLiveData) { it.networkState },
                totalCount = switchMap(sourceFactory.sourceLiveData) { it.totalCount },
                retry = { sourceFactory.sourceLiveData.value?.retryAllFailed() }
            )

        result.value = listing
    }
}