package by.enolizard.newsaggregator.base

class State<T>(
    val status: T,
    val error: Throwable? = null
)

enum class Status { LOADING, SUCCESS, ERROR }
enum class StatusWithEmpty { LOADING, SUCCESS, ERROR, EMPTY }
