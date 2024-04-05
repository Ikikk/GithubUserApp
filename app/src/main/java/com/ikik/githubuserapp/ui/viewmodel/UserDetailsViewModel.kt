package com.ikik.githubuserapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ikik.githubuserapp.data.local.entity.FavoriteUser
import com.ikik.githubuserapp.data.local.repository.FavoriteUserRepository
import com.ikik.githubuserapp.data.remote.response.DetailResponse
import com.ikik.githubuserapp.data.remote.retrofit.APIConfig
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserDetailsViewModel(private val repository: FavoriteUserRepository) : ViewModel() {
    private val _userDetails = MutableLiveData<DetailResponse>()
    val userDetails: LiveData<DetailResponse> = _userDetails

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun fetchUserDetails(username: String) {
        _isLoading.value = true
        val apiService = APIConfig.getAPIService()
        apiService.getDetail(username).enqueue(object : Callback<DetailResponse> {
            override fun onResponse(call: Call<DetailResponse>, response: Response<DetailResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _userDetails.value = response.body()
                } else {
                    Log.e("UserDetailsViewModel", "onResponse: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e("UserDetailsViewModel", "onFailure: ${t.message}", t)
            }
        })
    }

    fun addFavorite(fav: FavoriteUser) {
        viewModelScope.launch {
            repository.insert(fav)
        }
    }

    fun removeFavorite(fav: FavoriteUser) {
        viewModelScope.launch {
            repository.delete(fav)
        }
    }

    fun getByUsername(username: String) = repository.getByUsername(username)
}