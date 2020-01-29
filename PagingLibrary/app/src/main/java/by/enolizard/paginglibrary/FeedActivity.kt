package by.enolizard.paginglibrary

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import by.enolizard.paginglibrary.databinding.FeedActivityBinding
import javax.inject.Inject

class FeedActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: FeedActivityBinding
    private lateinit var viewModel: FeedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FeedActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        (application as App).appComponent.inject(this)
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(FeedViewModel::class.java)

        setupListeners()
    }

    private fun setupListeners() {
        val feedListAdapter = UnpagedListAdapter()

        with(binding.rvFeeds) {
            adapter = feedListAdapter;
            layoutManager = LinearLayoutManager(this@FeedActivity)
            setHasFixedSize(true)
        }

        viewModel.feeds.observe(this, Observer {
            feedListAdapter.setData(it)
        })

    }
}
