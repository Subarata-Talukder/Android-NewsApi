package com.softlabio.newsapi.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.softlabio.newsapi.R
import com.softlabio.newsapi.ResultOf
import com.softlabio.newsapi.adapter.ArticleAdapter
import kotlinx.android.synthetic.main.fragment_article.*
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseFragment(R.layout.fragment_home) {

    lateinit var newAdapter: ArticleAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclerView()

        newAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }
            findNavController().navigate(
                R.id.action_homeFragment_to_articleFragment,
                bundle
            )
        }

        viewModel.article.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is ResultOf.Success -> {
                    hideProgressBar()
                    result.value.let { newsResponse ->
                        newAdapter.listDiff.submitList(newsResponse.articles)
                    }
                }
                is ResultOf.Failure -> {

                    hideProgressBar()
                    result.message.let { message ->
                        Log.d("E-MSG", "Error occured")
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

    private fun setRecyclerView() {
        newAdapter = ArticleAdapter()
        rvHome.apply {
            adapter = newAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}