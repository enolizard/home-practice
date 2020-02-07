package by.enolizard.newsaggregator.presentation.adapters.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.enolizard.newsaggregator.base.PagingState
import by.enolizard.newsaggregator.base.PagingState.*
import by.enolizard.newsaggregator.databinding.PagingItemBinding
import by.enolizard.newsaggregator.presentation.invisible
import by.enolizard.newsaggregator.presentation.visible

class PagingHolder(
    private val binding: PagingItemBinding,
    private val onRetryClick: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.tvRetry.setOnClickListener {
            binding.tvMsg.invisible()
            binding.tvRetry.invisible()
            onRetryClick()
        }
    }

    fun bind(state: PagingState) {
        when (state) {
            Gone -> {
                throw IllegalArgumentException()
            }
            Loading -> {
                binding.tvMsg.invisible()
                binding.tvRetry.invisible()
                binding.pbLoading.visible()
            }
            is Error -> {
                binding.pbLoading.invisible()
                binding.tvMsg.visible()
                binding.tvRetry.visible()
            }
        }
    }

    companion object {

        fun create(
            parent: ViewGroup,
            onRetryClick: () -> Unit,
            attachToRoot: Boolean = false
        ): PagingHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = PagingItemBinding.inflate(layoutInflater, parent, attachToRoot)

            return PagingHolder(binding, onRetryClick)
        }
    }
}
