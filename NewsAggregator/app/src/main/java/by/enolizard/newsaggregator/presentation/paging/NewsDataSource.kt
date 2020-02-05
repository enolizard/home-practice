package by.enolizard.newsaggregator.presentation.paging

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import by.enolizard.newsaggregator.api.NewsApi
import by.enolizard.newsaggregator.api.response.Article
import by.enolizard.newsaggregator.base.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class NewsDataSource(
    private val newsApi: NewsApi
) : PageKeyedDataSource<Int, Article>() {

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
        callback: LoadInitialCallback<Int, Article>
    ) {
        _initialState.postValue(Loading)

        val initialLoading = newsApi.getFeeds("bitcoin", 1)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                callback.onResult(it.articles, null, 1)
                if (it.totalCount == 0) _initialState.postValue(Data(it.totalCount))
                else _initialState.postValue(EmptyData)
            }, {
                retry = { loadInitial(params, callback) }
                _initialState.postValue(Error(it))
            })
        rxBag.add(initialLoading)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Article>) {
        _paginatedState.postValue(Loading)

        val rangeLoading = newsApi.getFeeds("bitcoin", params.key)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                callback.onResult(it.articles, params.key + 1)
                _paginatedState.postValue(Success)
            }, {
                retry = { loadAfter(params, callback) }
                _paginatedState.postValue(Error(it))
            })
        rxBag.add(rangeLoading)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Article>) {}
}
