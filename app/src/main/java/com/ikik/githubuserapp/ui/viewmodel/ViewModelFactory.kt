package com.ikik.githubuserapp.ui.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ikik.githubuserapp.data.local.repository.FavoriteUserRepository

class ViewModelFactory (private val application: Application) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListFavoritesViewModel::class.java)) {
            return ListFavoritesViewModel(FavoriteUserRepository.getInstance(application)) as T
        } else if (modelClass.isAssignableFrom(UserDetailsViewModel::class.java)) {
            return UserDetailsViewModel(FavoriteUserRepository.getInstance(application)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(application: Application): ViewModelFactory {
            if (instance == null) {
                synchronized(ViewModelFactory::class.java) {
                    instance = ViewModelFactory(application)
                }
            }
            return instance as ViewModelFactory
        }
    }
}