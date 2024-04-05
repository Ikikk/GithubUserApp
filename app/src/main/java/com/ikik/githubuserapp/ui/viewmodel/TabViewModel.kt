package com.ikik.githubuserapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ikik.githubuserapp.data.remote.response.ItemsItem
import com.ikik.githubuserapp.data.remote.retrofit.APIConfig
import com.ikik.githubuserapp.ui.adapter.UserAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TabViewModel : ViewModel() {
    private val _users = MutableLiveData<List<ItemsItem>>()
    val users: LiveData<List<ItemsItem>> = _users

    private val _isLoadingFollowers = MutableLiveData<Boolean>()
    val isLoadingFollowers: LiveData<Boolean> = _isLoadingFollowers

    private val apiService = APIConfig.getAPIService()
    private lateinit var adapter: UserAdapter

    fun searchFollowers(username: String) {
        if (username.isEmpty()) {
            adapter.updateUsers(emptyList())
            _isLoadingFollowers.value = false
            return
        }
        apiService.getFollower(username).enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(call: Call<List<ItemsItem>>, response: Response<List<ItemsItem>>) {
                _isLoadingFollowers.value = false
                if (response.isSuccessful) {
                    _users.value = response.body() ?: emptyList()
                } else {
                    Log.e("TabViewModel", "searchFollowers - onResponse: Response not successful")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoadingFollowers.value = false
                Log.e("TabViewModel", "searchFollowers - onFailure", t)
            }
        })
    }

}