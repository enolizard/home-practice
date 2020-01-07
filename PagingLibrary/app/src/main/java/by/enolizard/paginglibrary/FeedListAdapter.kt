package by.enolizard.paginglibrary

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import by.enolizard.paginglibrary.api.response.FeedsPage
import by.enolizard.paginglibrary.base.DiffUtilCallback
import by.enolizard.paginglibrary.base.DiffUtilItemCallback
import by.enolizard.paginglibrary.databinding.FeedItemBinding

class FeedListAdapter : PagedListAdapter<FeedsPage.Article, FeedListAdapter.ViewHolder>(
    DiffUtilItemCallback { it: FeedsPage.Article -> it.url }
) {

    private val items: List<FeedsPage.Article> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = FeedItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    class ViewHolder(private val binding: FeedItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: FeedsPage.Article) {
            binding.tvMessage.text = item.title
        }
    }
}