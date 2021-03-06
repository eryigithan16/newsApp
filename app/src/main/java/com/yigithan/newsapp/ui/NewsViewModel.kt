package com.yigithan.newsapp.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yigithan.newsapp.models.Article
import com.yigithan.newsapp.models.NewsResponse
import com.yigithan.newsapp.repository.NewsRepository
import com.yigithan.newsapp.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(val newsRepository : NewsRepository) : ViewModel() {

    val searchNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var searchNewsPage = 1
    var searchNewsResponse: NewsResponse? = null

    fun searchNews(searchQuery: String) = viewModelScope.launch {

        searchNews.postValue(Resource.Loading())
        val response = newsRepository.searchNews(searchQuery, searchNewsPage)
        searchNews.postValue(handleSearchNewsResponse(response))
    }

    private fun handleSearchNewsResponse(response: Response<NewsResponse>) : Resource<NewsResponse>{
        if(response.isSuccessful){
            response.body()?.let { resultResponse ->
                searchNewsPage++
                if (searchNewsResponse == null){
                    searchNewsResponse = resultResponse
                } else{
                    val oldArticles = searchNewsResponse?.articles
                    val newArticles = resultResponse.articles
                    oldArticles?.addAll(newArticles)
                }

                return Resource.Success(searchNewsResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    fun saveArticle(article: Article) = viewModelScope.launch {
        newsRepository.upsert(article)
    }


    fun getSavedNews() = newsRepository.getSavedNews()

    fun deleteArticle(article: Article) = viewModelScope.launch {
        newsRepository.deleteArticle(article)
    }

}