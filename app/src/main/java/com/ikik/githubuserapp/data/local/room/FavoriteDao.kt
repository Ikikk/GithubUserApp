package com.ikik.githubuserapp.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ikik.githubuserapp.data.local.entity.FavoriteUser

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addFavorite(user: FavoriteUser)

    @Delete
    fun removeFavorite(user: FavoriteUser)

    @Query("SELECT * FROM favorite_users")
    fun getAllFavorites(): LiveData<List<FavoriteUser>>

    @Query("SELECT * FROM favorite_users WHERE username = :username")
    fun getFavoriteUserByUsername(username: String): LiveData<FavoriteUser>
}