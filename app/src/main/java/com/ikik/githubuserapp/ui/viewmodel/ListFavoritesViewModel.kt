package com.ikik.githubuserapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ikik.githubuserapp.data.local.entity.FavoriteUser
import com.ikik.githubuserapp.data.local.repository.FavoriteUserRepository

class ListFavoritesViewModel(private val repository: FavoriteUserRepository) : ViewModel() {
    fun getAllFavorites(): LiveData<List<FavoriteUser>> = repository.getAllFavorites()
}
