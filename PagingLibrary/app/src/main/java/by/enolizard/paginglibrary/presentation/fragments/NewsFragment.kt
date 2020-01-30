package by.enolizard.paginglibrary.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import by.enolizard.paginglibrary.R
import by.enolizard.paginglibrary.presentation.inflate

class NewsFragment : Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return container?.inflate(R.layout.fragment_news)
        //return super.onCreateView(inflater, container, savedInstanceState)
    }
}