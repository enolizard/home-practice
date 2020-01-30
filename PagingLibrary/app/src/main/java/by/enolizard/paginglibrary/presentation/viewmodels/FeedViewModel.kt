package by.enolizard.paginglibrary.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.enolizard.paginglibrary.api.NewsApi
import by.enolizard.paginglibrary.api.response.Article
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class FeedViewModel @Inject constructor(
    private val api: NewsApi
) : ViewModel() {
    private val _feeds = MutableLiveData<List<Article>>()
    val feeds: LiveData<List<Article>> get() = _feeds

    init {
        val k = api.getFeeds(
            "movies",
            "a18675c1319c4745b13fc3b0f06e382d"
            , 1, 100
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({ it ->
                _feeds.value = it.articles
            },{
              it.printStackTrace()
            })
    }
}