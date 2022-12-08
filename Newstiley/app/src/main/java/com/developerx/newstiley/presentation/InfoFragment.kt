package com.developerx.newstiley.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import com.developerx.newstiley.MainActivity
import com.developerx.newstiley.data.model.Article
import com.developerx.newstiley.databinding.FragmentInfoBinding
import com.developerx.newstiley.presentation.viewmodel.NewsViewModel
import com.google.android.material.snackbar.Snackbar

class InfoFragment : Fragment() {

    private var _binding : FragmentInfoBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel : NewsViewModel
    val args : InfoFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentInfoBinding.inflate(inflater, container, false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel

        val article = args.selectedArticle
        binding.webView.webViewClient = WebViewClient()
        if(article.url != null  && article.url != "") {
            binding.webView.loadUrl(article.url)
        }


        binding.saveFab.setOnClickListener {
            viewModel.saveArticle(article)
            Snackbar.make(view, "Successfully saved!", Snackbar.LENGTH_SHORT).show()
        }


   }

}