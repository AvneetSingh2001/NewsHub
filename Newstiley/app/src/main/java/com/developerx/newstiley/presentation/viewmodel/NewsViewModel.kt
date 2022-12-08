package com.developerx.newstiley.presentation.viewmodel

import android.app.Application
import android.content.Context
import android.content.res.Resources
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.*
import com.developerx.newstiley.data.model.APIResponse
import com.developerx.newstiley.data.model.Article
import com.developerx.newstiley.data.util.Resource
import com.developerx.newstiley.domain.usecase.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.http.Query

class NewsViewModel(
    private val app: Application,
    private val getNewsHeadlineUseCase : GetNewsHeadlinesUseCase,
    private val getSearchedNewsUseCase: GetSearchedNewsUseCase,
    private val saveNewsUseCase: SaveNewsUseCase,
    private val getSavedNewsUseCase: GetSavedNewsUseCase,
    private val deleteSavedNewsUseCase: DeleteSavedNewsUseCase
): AndroidViewModel(app) {

    val newsHeadlines : MutableLiveData<Resource<APIResponse>> = MutableLiveData()

    fun getNewsHeadlines(country: String, page: Int) = viewModelScope.launch(Dispatchers.IO) {
        newsHeadlines.postValue(Resource.Loading())

        try {
            if(checkForInternet(app)) {
                val apiResult = getNewsHeadlineUseCase.execute(country, page)
                newsHeadlines.postValue(apiResult)
            } else {
                newsHeadlines.postValue(Resource.Error("Internet Unavailable"))
            }
        } catch(e: Exception) {
            newsHeadlines.postValue(Resource.Error(e.message.toString()))
        }
    }

    private fun checkForInternet(context: Context): Boolean {

        // register activity with the connectivity manager service
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        // if the android version is equal to M
        // or greater we need to use the
        // NetworkCapabilities to check what type of
        // network has the internet connection
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            // Returns a Network object corresponding to
            // the currently active default data network.
            val network = connectivityManager.activeNetwork ?: return false

            // Representation of the capabilities of an active network.
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

            return when {
                // Indicates this network uses a Wi-Fi transport,
                // or WiFi has network connectivity
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true

                // Indicates this network uses a Cellular transport. or
                // Cellular has network connectivity
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true

                // else return false
                else -> false
            }
        } else {
            // if the android version is below M
            @Suppress("DEPRECATION") val networkInfo =
                connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }

    // for search -------------

    val searchNews: MutableLiveData<Resource<APIResponse>> = MutableLiveData()

    fun searchNews(
        country:String,
        searchQuery: String,
        page: Int
    ) = viewModelScope.launch {
        searchNews.postValue(Resource.Loading())

        try {
            if(checkForInternet(app)) {
                val apiResult = getSearchedNewsUseCase.execute(country, searchQuery,page)
                searchNews.postValue(apiResult)
            } else {
                searchNews.postValue(Resource.Error("Internet Unavailable"))
            }
        } catch(e: Exception) {
            searchNews.postValue(Resource.Error(e.message.toString()))
        }
    }

    // local database -----

    fun saveArticle(article: Article) = viewModelScope.launch(Dispatchers.IO) {
        saveNewsUseCase.execute(article)
    }


    fun getSavedNews() = liveData {
        getSavedNewsUseCase.execute().collect {
            emit(it)
        }
    }


    fun deleteArticle(article: Article) = viewModelScope.launch(Dispatchers.IO) {
        deleteSavedNewsUseCase.execute(article)
    }

}