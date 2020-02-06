package by.enolizard.newsaggregator.presentation.paging

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import by.enolizard.newsaggregator.api.NewsApi
import by.enolizard.newsaggregator.api.response.Feed
import by.enolizard.newsaggregator.base.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class NewsDataSource(
    private val newsApi: NewsApi
) : PageKeyedDataSource<Int, Feed>() {

    private val rxBag = CompositeDisposable()
    private val _paginatedState = MutableLiveData<State>()
    private val _initialState = MutableLiveData<State>()
    private var retry: (() -> Any)? = null

    val paginatedState: LiveData<State> get() = _paginatedState
    val initialState: LiveData<State> get() = _initialState

    fun retryAreFailed() {
        retry?.invoke()
        retry = null
    }

    override fun invalidate() {
        super.invalidate()
        rxBag.clear()
    }

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Feed>
    ) {
        _initialState.postValue(State.Loading)

        val initialLoading = newsApi.getFeeds("bitcoin", 1,15)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                callback.onResult(it.feeds, null, 2)
                if (it.totalCount == 0) _initialState.postValue(State.Data(it.totalCount))
                else _initialState.postValue(State.Empty)
            }, {
                retry = { loadInitial(params, callback) }
                _initialState.postValue(State.Error(it))
            })
        rxBag.add(initialLoading)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Feed>) {
        _paginatedState.postValue(State.Loading)

        val rangeLoading = newsApi.getFeeds("bitcoin", params.key,15)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                callback.onResult(it.feeds, params.key + 1)
                _paginatedState.postValue(State.Success)
            }, {
                retry = { loadAfter(params, callback) }
                _paginatedState.postValue(State.Error(it))
            })
        rxBag.add(rangeLoading)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Feed>) {}
}
