package com.ikik.githubuserapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ikik.githubuserapp.data.remote.response.ItemsItem
import com.ikik.githubuserapp.data.remote.response.SearchResponse
import com.ikik.githubuserapp.data.remote.retrofit.APIConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel(){
    private val _users: MutableLiveData<List<ItemsItem>> = MutableLiveData()
    val users: LiveData<List<ItemsItem>> = _users

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var lastQuery: String = ""

    fun searchUsers(query: String) {
        lastQuery = query
        APIConfig.getAPIService().getSearchUser(query).enqueue(object : Callback<SearchResponse> {
            override fun onResponse(call: Call<SearchResponse>, response: Response<SearchResponse>) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    _users.postValue(response.body()?.items)
                } else {
                    _isLoading.value = false
                    Log.e("MainViewModel", "onResponse: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e("MainViewModel", "onFailure: ${t.message}", t)
            }
        })
    }

    fun restoreSearch() {
        searchUsers(lastQuery)
    }
}