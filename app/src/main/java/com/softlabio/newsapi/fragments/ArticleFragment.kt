package com.softlabio.newsapi.fragments

import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.softlabio.newsapi.R
import kotlinx.android.synthetic.main.fragment_article.*

class ArticleFragment : BaseFragment(R.layout.fragment_article) {

    val args: ArticleFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val article = args.article

        webView.apply {
            webViewClient = WebViewClient()

            article?.let {
                loadUrl(article.url)
            }
        }

        fab.setOnClickListener {
            article?.let { viewModel.saveArticle(article) }
            Snackbar.make(view, "Successful",Snackbar.LENGTH_LONG).show()
        }
    }
}