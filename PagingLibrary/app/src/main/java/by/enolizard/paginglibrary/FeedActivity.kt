package by.enolizard.paginglibrary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import by.enolizard.paginglibrary.api.response.FeedsPage
import by.enolizard.paginglibrary.databinding.FeedActivityBinding
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FeedActivity : AppCompatActivity() {

    private lateinit var binding: FeedActivityBinding
    private lateinit var viewModel: FeedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FeedActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProviders.of(this).get(FeedViewModel::class.java)

        setupListeners()
    }

    private fun setupListeners() {
        with(binding.rvFeeds) {
            adapter = FeedListAdapter();
            layoutManager = LinearLayoutManager(this@FeedActivity)
            setHasFixedSize(true)
        }

        App.api.getFeeds(
            "movies",
            "a18675c1319c4745b13fc3b0f06e382d"
            , 1, 10
        ).enqueue(object : Callback<FeedsPage> {
            override fun onFailure(call: Call<FeedsPage>, t: Throwable) {
            }

            override fun onResponse(call: Call<FeedsPage>, response: Response<FeedsPage>) {
            }

        })


    }
}
