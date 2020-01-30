package by.enolizard.newsaggregator.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import by.enolizard.newsaggregator.R
import by.enolizard.newsaggregator.presentation.inflate

class FavoriteFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return container?.inflate(R.layout.fragment_favorite)
        //return super.onCreateView(inflater, container, savedInstanceState)
    }
}