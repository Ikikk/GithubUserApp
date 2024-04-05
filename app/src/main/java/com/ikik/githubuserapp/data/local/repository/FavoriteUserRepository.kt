package com.ikik.githubuserapp.data.local.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.ikik.githubuserapp.data.local.room.FavoriteRoomDatabase
import com.ikik.githubuserapp.data.local.entity.FavoriteUser
import com.ikik.githubuserapp.data.local.room.FavoriteDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.concurrent.Volatile

class FavoriteUserRepository private constructor (private val favoriteDao: FavoriteDao) {
    fun getAllFavorites() : LiveData<List<FavoriteUser>> = favoriteDao.getAllFavorites()

    suspend fun insert(fav: FavoriteUser) {
        withContext(Dispatchers.IO) {
            favoriteDao.addFavorite(fav)
        }
    }

    suspend fun delete(fav: FavoriteUser) {
        withContext(Dispatchers.IO) {
            favoriteDao.removeFavorite(fav)
        }
    }

    fun getByUsername(username: String) : LiveData<FavoriteUser> = favoriteDao.getFavoriteUserByUsername(username)

    companion object {
        @Volatile
        private var instance: FavoriteUserRepository? = null

        fun getInstance(
            application: Application
        ): FavoriteUserRepository =
            instance ?: synchronized(this) {
                instance ?: FavoriteUserRepository(FavoriteRoomDatabase.getDatabase(application).favoriteUserDao())
            }.also { instance= it }
    }

}