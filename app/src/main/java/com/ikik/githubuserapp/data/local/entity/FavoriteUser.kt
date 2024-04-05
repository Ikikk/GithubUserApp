package com.ikik.githubuserapp.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "favorite_users")
@Parcelize
data class FavoriteUser(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "username")
    val username: String = "",
    @ColumnInfo(name = "avatarUrl")
    val avatarUrl: String? = null,
    @ColumnInfo(name = "url")
    val url: String? = null
) :Parcelable
