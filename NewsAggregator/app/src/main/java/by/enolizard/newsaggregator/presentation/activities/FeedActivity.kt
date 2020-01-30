package by.enolizard.newsaggregator.presentation.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import by.enolizard.newsaggregator.App
import by.enolizard.newsaggregator.databinding.FeedActivityBinding
import by.enolizard.newsaggregator.presentation.adapters.UnpagedListAdapter
import by.enolizard.newsaggregator.presentation.viewmodels.FeedViewModel
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
            adapter = feedListAdapter
            layoutManager = LinearLayoutManager(this@FeedActivity)
            setHasFixedSize(true)
        }

        binding.btnNext.setOnClickListener {
            val int = Intent(this, WorkActivity::class.java)
            startActivity(int)
        }

        viewModel.feeds.observe(this, Observer {
            feedListAdapter.setData(it)
        })

    }
}
