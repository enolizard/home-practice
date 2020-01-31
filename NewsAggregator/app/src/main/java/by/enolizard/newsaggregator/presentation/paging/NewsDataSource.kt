package by.enolizard.newsaggregator.presentation.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import by.enolizard.newsaggregator.api.NewsApi
import by.enolizard.newsaggregator.api.response.Article
import by.enolizard.newsaggregator.base.State
import by.enolizard.newsaggregator.base.Status
import by.enolizard.newsaggregator.base.StatusWithEmpty
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class NewsDataSource(
    private val newsApi: NewsApi
) : PageKeyedDataSource<Int, Article>() {

    private val rxBag = CompositeDisposable()
    private val paginatedNetworkState = MutableLiveData<State<Status>>()
    private val initialNetworkState = MutableLiveData<State<StatusWithEmpty>>()
    private var retry: (() -> Any)? = null

    fun retryAreFailed() {
        retry?.invoke()
        retry = null
    }

    fun clear() {
        rxBag.clear()
    }

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Article>
    ) {
        initialNetworkState.postValue(State(StatusWithEmpty.LOADING))

        val initialLoading = newsApi.getFeeds("bitcoin", 0)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                callback.onResult(it.articles, null, 1)
                if (it.totalCount == 0) initialNetworkState.postValue(State(StatusWithEmpty.SUCCESS))
                else initialNetworkState.postValue(State(StatusWithEmpty.EMPTY))
            }, {
                retry = { loadInitial(params, callback) }
                initialNetworkState.postValue(State(StatusWithEmpty.ERROR, it))
            })
        rxBag.add(initialLoading)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Article>) {
        paginatedNetworkState.postValue(State(Status.LOADING))

        val rangeLoading = newsApi.getFeeds("bitcoin", params.key)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                callback.onResult(it.articles, params.key + 1)
                paginatedNetworkState.postValue(State(Status.SUCCESS))
            }, {
                retry = { loadAfter(params, callback) }
                paginatedNetworkState.postValue(State(Status.ERROR, it))
            })
        rxBag.add(rangeLoading)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Article>) {}
}
