package by.enolizard.newsaggregator.presentation.adapters.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.enolizard.newsaggregator.api.response.Feed
import by.enolizard.newsaggregator.databinding.FeedItemBinding
import com.squareup.picasso.Picasso

class FeedHolder private constructor(
    private val binding: FeedItemBinding,
    private val onSpeechClick: (position: Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Feed?) {
        if (item != null) {
            binding.tvDescription.text = item.description
            binding.tvPublishedAt.text = "${item.date} by ${item.author}"
            binding.tvSourceName.text = item.source.name
            binding.tvTitle.text = item.title
            binding.btnSpeech.setOnClickListener { onSpeechClick(adapterPosition) }

            Picasso.get()
                .load(item.urlToImage)
                .noFade()
                .into(binding.ivPhoto)
        }
    }

    companion object {

        fun create(
            parent: ViewGroup, attachToRoot: Boolean = false,
            onPositionSpeechClick: (position: Int) -> Unit
        ): FeedHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = FeedItemBinding.inflate(layoutInflater, parent, attachToRoot)

            return FeedHolder(binding, onPositionSpeechClick)
        }
    }
}
