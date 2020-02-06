package by.enolizard.newsaggregator.presentation.adapters

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import by.enolizard.newsaggregator.R
import by.enolizard.newsaggregator.api.response.Feed
import by.enolizard.newsaggregator.base.PagingState
import by.enolizard.newsaggregator.base.PagingState.Gone
import by.enolizard.newsaggregator.presentation.adapters.viewholders.FeedHolder
import by.enolizard.newsaggregator.presentation.adapters.viewholders.PagingHolder

class FeedsPagedAdapter(
    private val onRetryClick: () -> Unit
) : PagedListAdapter<Feed, RecyclerView.ViewHolder>(COMPARATOR) {

    private var pagingState: PagingState = Gone

    fun updateState(newState: PagingState) {
        val previousState = this.pagingState
        val hadExtraRow = hasExtraRow()

        this.pagingState = newState
        val hasExtraRow = hasExtraRow()

        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(super.getItemCount())
            } else {
                notifyItemInserted(super.getItemCount())
            }
        } else if (hasExtraRow && previousState != newState) {
            notifyItemChanged(itemCount - 1)
        }
    }

    private fun hasExtraRow(): Boolean = pagingState != Gone

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            R.layout.item_feed -> FeedHolder.create(parent)
            R.layout.item_paging -> PagingHolder.create(parent, onRetryClick)
            else -> throw IllegalArgumentException()
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.item_paging -> (holder as PagingHolder).bind(pagingState)
            R.layout.item_feed -> (holder as FeedHolder).bind(getItem(position))
        }
    }

    override fun getItemViewType(position: Int): Int =
        if (hasExtraRow() && position == itemCount - 1) R.layout.item_paging
        else R.layout.item_feed

    override fun getItemCount(): Int =
        super.getItemCount() + if (hasExtraRow()) 1 else 0

    companion object {

        private val COMPARATOR = object : DiffUtil.ItemCallback<Feed>() {

            override fun areItemsTheSame(oldItem: Feed, newItem: Feed): Boolean =
                oldItem.hashCode() == newItem.hashCode()

            override fun areContentsTheSame(oldItem: Feed, newItem: Feed): Boolean =
                oldItem == newItem
        }
    }
}
