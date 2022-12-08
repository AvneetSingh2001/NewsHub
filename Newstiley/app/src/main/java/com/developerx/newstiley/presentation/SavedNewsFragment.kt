package com.developerx.newstiley.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.developerx.newstiley.MainActivity
import com.developerx.newstiley.R
import com.developerx.newstiley.databinding.FragmentSavedNewsBinding
import com.developerx.newstiley.presentation.adapter.NewsAdapter
import com.developerx.newstiley.presentation.viewmodel.NewsViewModel
import com.google.android.material.snackbar.Snackbar


class SavedNewsFragment : Fragment() {

    private var _binding : FragmentSavedNewsBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: NewsViewModel
    private lateinit var newsAdapter : NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSavedNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
        newsAdapter = (activity as MainActivity).newsAdapter

        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply{
                putSerializable("selected_article",it)
            }
            requireView().findNavController().navigate(R.id.action_savedNewsFragment_to_infoFragment, bundle)
        }

        initRecyelerView()

        viewModel.getSavedNews().observe(viewLifecycleOwner) {
            newsAdapter.differ.submitList(it)
        }

        itemTouchCallBack(view)
    }

    private fun initRecyelerView() {
        binding.savedNewsListRecyclerView.adapter = newsAdapter
        binding.savedNewsListRecyclerView.layoutManager = LinearLayoutManager(activity)
    }


    private fun itemTouchCallBack(view: View) {
        val itemTouchHelperCallBack = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val article = newsAdapter.differ.currentList[position]
                viewModel.deleteArticle(article)
                Snackbar.make(view, "Deleted SuccessFully", Snackbar.LENGTH_LONG)
                    .apply {
                        setAction("UNDO") {
                            viewModel.saveArticle(article)
                        }
                    }
                    .show()
            }
        }

        ItemTouchHelper(itemTouchHelperCallBack).attachToRecyclerView(binding.savedNewsListRecyclerView)
    }
}