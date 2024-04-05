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

class TabFollowingViewModel : ViewModel() {
    private val _users = MutableLiveData<List<ItemsItem>>()
    val users: LiveData<List<ItemsItem>> = _users

    private val _isLoadingFollowing = MutableLiveData<Boolean>()
    val isLoadingFollowing: LiveData<Boolean> = _isLoadingFollowing

    private val apiService = APIConfig.getAPIService()
    private lateinit var adapter: UserAdapter

    fun searchFollowing(username: String) {
        _isLoadingFollowing.value = true
        if (username.isEmpty()) {
            adapter.updateUsers(emptyList())
            _isLoadingFollowing.value = false
            return
        }

        _isLoadingFollowing.value = true
        apiService.getFollowing(username).enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(call: Call<List<ItemsItem>>,response: Response<List<ItemsItem>>) {
                if (response.isSuccessful) {
                    _isLoadingFollowing.value = false
                    _users.value = response.body() ?: emptyList()
                } else {
                    _isLoadingFollowing.value = false
                    Log.e("TabViewModel", "searchFollowing - onResponse: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>,t: Throwable) {
                _isLoadingFollowing.value = false
                Log.e("TabViewModel", "searchFollowing - onFailure: ${t.message}", t)
            }
        })

    }
}