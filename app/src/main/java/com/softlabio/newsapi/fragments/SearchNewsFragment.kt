package com.softlabio.newsapi.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.softlabio.newsapi.R
import com.softlabio.newsapi.ResultOf
import com.softlabio.newsapi.adapter.ArticleAdapter
import kotlinx.android.synthetic.main.fragment_search_news.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchNewsFragment : BaseFragment(R.layout.fragment_search_news) {
    val TAG = "SearchNewsFragment"
    lateinit var newsAdapter: ArticleAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }
            findNavController().navigate(
                R.id.action_searchNewsFragment_to_articleFragment,
                bundle
            )
        }

        var job: Job? =null
        etSearch.addTextChangedListener{ watcher->
            job?.cancel()
            job = MainScope().launch {
                delay(500L)
                watcher?.let {
                    if(watcher.toString().isNotEmpty()) {
                        viewModel.searchNews(watcher.toString())
                    }
                }
            }
        }

        viewModel.searchNews.observe(viewLifecycleOwner, Observer { response ->
            when(response) {
                is ResultOf.Success -> {
                    hideProgressBar()
                    response.value?.let { newsResponse ->
                        newsAdapter.listDiff.submitList(newsResponse.articles)
                    }
                }
                is ResultOf.Failure -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Log.e(TAG, "An error occured: $message")
                    }
                }
                is ResultOf.Loading -> {
                    showProgressBar()
                }
            }
        })
    }

    private fun hideProgressBar() {
        paginationProgressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        paginationProgressBar.visibility = View.VISIBLE
    }

    private fun setupRecyclerView() {
        newsAdapter = ArticleAdapter()
        rvSearchNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}