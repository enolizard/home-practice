package by.enolizard.newsaggregator.base

sealed class State {
    object Loading : State()
    object Success : State()
    class Error(val error: Throwable) : State()
    class Data<T>(val data: T) : State()
    object Empty : State()
}


sealed class PagingState {
    object Loading : PagingState()
    class Error(val msg: String) : PagingState()
    object Gone : PagingState()
}
