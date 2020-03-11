package by.enolizard.newsaggregator.presentation.fragments

import android.content.Intent
import android.graphics.Rect
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
import by.enolizard.newsaggregator.api.response.Feed
import by.enolizard.newsaggregator.base.PaginatedState
import by.enolizard.newsaggregator.databinding.NewsFragmentBinding
import by.enolizard.newsaggregator.presentation.adapters.FeedsPagedAdapter
import by.enolizard.newsaggregator.presentation.adapters.FeedsPagedAdapter.ClickMode
import by.enolizard.newsaggregator.presentation.showToast
import by.enolizard.newsaggregator.presentation.viewmodels.NewsViewModel
import by.enolizard.newsaggregator.presentation.views.SpaceItemDecoration
import java.util.*
import javax.inject.Inject


class NewsFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: NewsFragmentBinding
    private lateinit var viewModel: NewsViewModel

    private lateinit var speaker: TextToSpeech

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = NewsFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity!!.application as App).appComponent.inject(this)
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(NewsViewModel::class.java)

        initTextToSpeech()
        setupListeners()
    }

    override fun onPause() {
        super.onPause()
        speaker.stop()
    }

    override fun onDestroy() {
        super.onDestroy()
        speaker.shutdown()
    }

    private fun initTextToSpeech() {
        speaker = TextToSpeech(context) {
            if (it == TextToSpeech.SUCCESS) {
                val result = speaker.setLanguage(Locale.ENGLISH)
                speaker.setPitch(1f);
                speaker.setSpeechRate(1.3f);
            }
        }
    }

    private fun setupListeners() {
        val feedListAdapter = FeedsPagedAdapter(
            onRetryClick = { viewModel.retry() },
            onItemClick = { feed: Feed?, clickMode: ClickMode ->
                when (clickMode) {
                    ClickMode.Speech -> {
                        speaker.speak(feed?.title, TextToSpeech.QUEUE_FLUSH, null, null)
                        speaker.speak(feed?.description, TextToSpeech.QUEUE_ADD, null, null)
                    }
                    ClickMode.Source -> TODO()
                    ClickMode.Feed -> {
                        val i = Intent(Intent.ACTION_VIEW)
                        i.data = Uri.parse(feed?.url)
                        startActivity(i)
                    }
                }
            }
        )

        with(binding.rvFeeds) {
            adapter = feedListAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            addItemDecoration(SpaceItemDecoration(context, outRectDp = Rect(0, 3, 0, 3)))
        }

        viewModel.initialState.observe(viewLifecycleOwner, Observer {

        })

        viewModel.paginatedState.observe(viewLifecycleOwner, Observer {
            if (it is PaginatedState.Error)
                context?.showToast(it.e.message ?: "Unknown error")
            feedListAdapter.updatePaginatedState(it)
        })

        viewModel.feeds.observe(viewLifecycleOwner, Observer {
            feedListAdapter.submitList(it)
        })
    }
}
