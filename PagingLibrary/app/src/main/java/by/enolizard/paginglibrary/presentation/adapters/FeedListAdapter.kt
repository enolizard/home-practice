package by.enolizard.paginglibrary.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import by.enolizard.paginglibrary.api.response.Article
import by.enolizard.paginglibrary.base.DiffUtilItemCallback
import by.enolizard.paginglibrary.databinding.FeedItemBinding

class FeedListAdapter : PagedListAdapter<Article, FeedListAdapter.ViewHolder>(
    DiffUtilItemCallback { it: Article -> it.url }
) {

    private val items: List<Article> = arrayListOf()

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

        fun bind(item: Article) {
            binding.tvMessage.text = item.title
        }
    }
}