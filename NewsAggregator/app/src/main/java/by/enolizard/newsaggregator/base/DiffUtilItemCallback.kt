package by.enolizard.newsaggregator.base

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil

@SuppressLint("DiffUtilEquals")
class DiffUtilItemCallback<T, R>(
    private val itemsTheSameSelector: (T) -> R
) : DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean =
        itemsTheSameSelector(oldItem) == itemsTheSameSelector(newItem)

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean =
        oldItem == newItem
}
