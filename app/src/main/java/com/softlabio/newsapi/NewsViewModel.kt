package com.softlabio.newsapi

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softlabio.newsapi.model.Article
import com.softlabio.newsapi.model.NewsResponse

import com.softlabio.newsapi.repository.NewsRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(
    val newsRepository: NewsRepository
) : ViewModel() {


    var pageNumber = 1
    val article: MutableLiveData<ResultOf<NewsResponse>> = MutableLiveData()
    val searchNews: MutableLiveData<ResultOf<NewsResponse>> = MutableLiveData()

    init {
        getArticle("us")
    }

    fun getArticle(countryCode: String) = viewModelScope.launch {
        article.postValue(ResultOf.Loading())
        val response = newsRepository.getArticle(countryCode, pageNumber)
        article.postValue(handleResponse(response))
    }

    fun searchNews(searchQuery: String) = viewModelScope.launch {
        searchNews.postValue(ResultOf.Loading())
        val response = newsRepository.searchNews(searchQuery, pageNumber)
        searchNews.postValue(handleResponse(response))
    }

    private fun handleResponse(response: Response<NewsResponse>): ResultOf<NewsResponse> {

        if (response.isSuccessful) {
            response.body()?.let { response ->
                return ResultOf.Success(response)
            }
        }

        return ResultOf.Failure(response.message())
    }

    fun saveArticle(article: Article) = viewModelScope.launch {
        newsRepository.upsert(article)
    }

    fun getArticle() = newsRepository.getArticles()


    fun deleteArticle(article: Article) = viewModelScope.launch {
        newsRepository.deleteArticle(article)
    }
}