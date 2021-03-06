package by.enolizard.newsaggregator.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.switchMap
import androidx.lifecycle.ViewModel
import androidx.paging.Config
import androidx.paging.PagedList
import androidx.paging.toLiveData
import by.enolizard.newsaggregator.api.NewsApi
import by.enolizard.newsaggregator.api.response.Feed
import by.enolizard.newsaggregator.base.Listing
import by.enolizard.newsaggregator.base.PaginatedState
import by.enolizard.newsaggregator.base.State
import by.enolizard.newsaggregator.presentation.paging.NewsDataSourceFactory
import javax.inject.Inject

class NewsViewModel @Inject constructor(
    private val api: NewsApi
) : ViewModel() {

    private val result = MutableLiveData<Listing<Feed>>()

    val feeds: LiveData<PagedList<Feed>> = switchMap(result) { it.pagedList }
    val initialState: LiveData<State> = switchMap(result) { it.initialState }
    val paginatedState: LiveData<PaginatedState> = switchMap(result) { it.paginatedState }

    init {
        loadNews()
    }

    fun retry(){
        result.value?.retry?.invoke()
    }

    private fun loadNews() {
        val sourceFactory = NewsDataSourceFactory(api)

        val pagedListConfig = Config(pageSize = 15, enablePlaceholders = false)

        val livePagedList = sourceFactory.toLiveData(pagedListConfig)

        val listing = Listing(
            pagedList = livePagedList,
            initialState = switchMap(sourceFactory.sourceLiveData) { it.initialState },
            paginatedState = switchMap(sourceFactory.sourceLiveData) { it.paginatedState },
            retry = { sourceFactory.sourceLiveData.value?.retryAreFailed() }
        )

        result.value = listing
    }

    companion object{

    }
}