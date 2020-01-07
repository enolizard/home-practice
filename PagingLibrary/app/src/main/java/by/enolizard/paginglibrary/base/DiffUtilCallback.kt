package by.enolizard.paginglibrary.base

import androidx.recyclerview.widget.DiffUtil

class DiffUtilCallback<T, R>(
    private val oldList: List<T>,
    private val newList: List<T>,
    private val itemsTheSameSelector: (T) -> R
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        itemsTheSameSelector(oldList[oldItemPosition]) == itemsTheSameSelector(newList[newItemPosition])

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]
}
