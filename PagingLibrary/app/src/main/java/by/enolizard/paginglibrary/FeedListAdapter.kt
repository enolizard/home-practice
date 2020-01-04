package by.enolizard.paginglibrary

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.enolizard.paginglibrary.databinding.FeedItemBinding

class FeedListAdapter
    : RecyclerView.Adapter<FeedListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = FeedItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    class ViewHolder(val binding: FeedItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            binding.tvMessage.text = "who are you?"
        }
    }
}