package by.enolizard.newsaggregator.presentation.adapters.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.enolizard.newsaggregator.api.response.Feed
import by.enolizard.newsaggregator.databinding.FeedItemBinding
import com.squareup.picasso.Picasso

class FeedHolder private constructor(
    private val binding: FeedItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Feed?) {
        if (item != null) {
            binding.tvDescription.text = item.description
            binding.tvPublishedAt.text = item.source.name
            binding.tvSourceName.text = item.author
            binding.tvTitle.text = item.title
            Picasso.get()
                .load(item.urlToImage)
                .noFade()
                .into(binding.ivPhoto)
        }
    }

    companion object {

        fun create(parent: ViewGroup, attachToRoot: Boolean = false): FeedHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = FeedItemBinding.inflate(layoutInflater, parent, attachToRoot)

            return FeedHolder(binding)
        }
    }
}
