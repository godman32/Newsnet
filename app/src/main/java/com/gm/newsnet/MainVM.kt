package com.gm.newsnet

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gm.newsnet.utills.Event
import com.gm.newsnet.utills.Resource
import com.gm.newsnet.utills.Utils
import com.gm.newsnet.app.MyApplication
import com.gm.newsnet.conn.AppRepository
import com.gm.newsnet.news.Articles
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

/**
 * Created by @godman on 04/07/23.
 */

class MainVM (app: Application, private val repository: AppRepository) : AndroidViewModel(app) {

    private val _atricles = MutableLiveData<Event<Resource<Articles>>>()
    val articles: LiveData<Event<Resource<Articles>>> = _atricles

    var page= 1
    var cat= "general"
    var search= ""

    fun getTopHeadlines() = viewModelScope.launch(Dispatchers.IO) {
        loadTopHeadlines()
    }






    private suspend fun loadTopHeadlines() {
        _atricles.postValue(Event(Resource.Loading()))
        if(search.equals("") && cat.equals("")){
            cat= "general"
        }

        try {
            if (Utils.hasInternetConnection(getApplication<MyApplication>())) {
                val response = repository.getTopHeadlines(cat, search, page)
                _atricles.postValue(handleArticles(response))
            } else {
                _atricles.postValue(Event(Resource.Error(getApplication<MyApplication>().getString(R.string.no_internet_connection))))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> {
                    _atricles.postValue(
                        Event(Resource.Error(
                            getApplication<MyApplication>().getString(
                                R.string.network_failure
                            )
                        ))
                    )
                }
                else -> {
                    _atricles.postValue(
                        Event(Resource.Error(
                            getApplication<MyApplication>().getString(
                                R.string.conversion_error
                            )
                        ))
                    )
                }
            }
        }
    }

    private fun handleArticles(response: Response<Articles>): Event<Resource<Articles>>? {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Event(Resource.Success(resultResponse))
            }
        }
        return Event(Resource.Error(response.message()))
    }

}