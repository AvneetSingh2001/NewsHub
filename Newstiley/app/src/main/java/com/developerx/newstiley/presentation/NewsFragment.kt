package com.developerx.newstiley.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.SearchView
import android.widget.SearchView.OnCloseListener
import android.widget.SearchView.OnQueryTextListener
import android.widget.Toast
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.lifecycle.Observer
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.developerx.newstiley.MainActivity
import com.developerx.newstiley.R
import com.developerx.newstiley.data.util.Resource
import com.developerx.newstiley.databinding.FragmentNewsBinding
import com.developerx.newstiley.presentation.adapter.NewsAdapter
import com.developerx.newstiley.presentation.viewmodel.NewsViewModel
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class NewsFragment : Fragment() {

    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: NewsViewModel
    private lateinit var adapter: NewsAdapter

    private var country = "us"
    private var page = 1
    private var isScrolling = false
    private var isLoading = false
    private var isLastPage = false
    private var pages = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        adapter = (activity as MainActivity).newsAdapter
        initRecyelerView()
        getNewsList()
        setSearchView()

        adapter.setOnItemClickListener {
            val bundle = Bundle().apply{
                putSerializable("selected_article",it)
            }
            requireView().findNavController().navigate(R.id.action_newsFragment_to_infoFragment, bundle)
        }
    }
    private fun initRecyelerView() {
        binding.newsListRecyclerView.adapter = adapter
        binding.newsListRecyclerView.layoutManager = LinearLayoutManager(activity)
        binding.newsListRecyclerView.addOnScrollListener(this@NewsFragment.onScrollListener)
    }

    private fun getNewsList() {
        viewModel.getNewsHeadlines(country,page)
        viewModel.newsHeadlines.observe(viewLifecycleOwner, Observer { response ->
            when(response) {
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let {
                        Toast.makeText(activity, "An Error Occurred : $it", Toast.LENGTH_LONG).show()
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let {
                        adapter.differ.submitList(it.articles.toList())
                        if(it.totalResults % 20 == 0) {
                            pages = it.totalResults / 20
                        } else {
                            pages = it.totalResults / 20 + 1
                        }
                        isLastPage = page == pages
                    }
                }
            }
        })
    }

    private fun showProgressBar() {
        isLoading = true
        binding.progCons.visibility = View.VISIBLE
    }


    private fun hideProgressBar() {
        isLoading = false
        binding.progCons.visibility = View.GONE
    }

    private val onScrollListener = object: RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager = binding.newsListRecyclerView.layoutManager as LinearLayoutManager
            val sizeOfTheCurrentList = layoutManager.itemCount
            val visibleItem = layoutManager.childCount
            val topPosition = layoutManager.findFirstVisibleItemPosition()

            val hasReachedToEnd = topPosition + visibleItem >= sizeOfTheCurrentList
            val shouldPaginate = !isLoading && !isLastPage && hasReachedToEnd && isScrolling
            
            if(shouldPaginate) {
                page++
                viewModel.getNewsHeadlines(country, page)
                isScrolling = false
            }
        }


    }


    //search ------

    private fun setSearchView() {
        binding.searchView.setOnQueryTextListener(
            object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    viewModel.searchNews(country, query.toString(), page)
                    viewSearchNews()
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    MainScope().launch {
                        delay(2000)
                        viewModel.searchNews(country, newText.toString(), page)
                        viewSearchNews()
                    }
                    return false
                }

            }
        )

        binding.searchView.setOnCloseListener {
            initRecyelerView()
            getNewsList()
            false
        }
    }

    fun viewSearchNews() {
        viewModel.searchNews.observe(viewLifecycleOwner, Observer { response ->
            when(response) {
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let {
                        Toast.makeText(activity, "An Error Occurred : $it", Toast.LENGTH_LONG).show()
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let {
                        adapter.differ.submitList(it.articles.toList())
                        if(it.totalResults % 20 == 0) {
                            pages = it.totalResults / 20
                        } else {
                            pages = it.totalResults / 20 + 1
                        }
                        isLastPage = page == pages
                    }
                }
            }
        })
    }

}