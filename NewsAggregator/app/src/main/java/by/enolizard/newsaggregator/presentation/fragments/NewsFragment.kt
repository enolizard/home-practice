package by.enolizard.newsaggregator.presentation.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import by.enolizard.newsaggregator.App
import by.enolizard.newsaggregator.base.PagingState
import by.enolizard.newsaggregator.databinding.NewsFragmentBinding
import by.enolizard.newsaggregator.presentation.adapters.FeedsPagedAdapter
import by.enolizard.newsaggregator.presentation.showToast
import by.enolizard.newsaggregator.presentation.viewmodels.NewsViewModel
import java.util.*
import javax.inject.Inject

class NewsFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: NewsFragmentBinding
    private lateinit var viewModel: NewsViewModel
    private lateinit var textSpeech: TextToSpeech

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = NewsFragmentBinding.inflate(layoutInflater)
        textSpeech = TextToSpeech(context, TextToSpeech.OnInitListener {
            if (it != TextToSpeech.ERROR) {
                textSpeech.setLanguage(Locale.UK);
            }
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity!!.application as App).appComponent.inject(this)
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(NewsViewModel::class.java)

        setupListeners()
    }

    private fun setupListeners() {
        val feedListAdapter = FeedsPagedAdapter(onRetryClick = { viewModel.retry() },
        onItemClick = {
                it?.let {
                    startActivity(Intent(Intent.ACTION_VIEW).setData(Uri.parse(it.url)))
                textSpeech.speak(it.description, TextToSpeech.QUEUE_FLUSH, null)
                }

        }
            )

        with(binding.rvFeeds) {
            adapter = feedListAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }

        viewModel.paginatedState.observe(viewLifecycleOwner, Observer {
            if (it is PagingState.Error)
                context?.showToast(it.e.message ?: "Internet error")
            feedListAdapter.updateState(it)
        })

        viewModel.feeds.observe(viewLifecycleOwner, Observer {
            feedListAdapter.submitList(it)
        })
    }
}
