package by.enolizard.newsaggregator.base

import androidx.lifecycle.LiveData
import androidx.paging.PagedList

class Listing<T>(
    val pagedList: LiveData<PagedList<T>>,
    val initialState: LiveData<State>,
    val paginatedState: LiveData<PagingState>,
    val retry: () -> Unit
)
