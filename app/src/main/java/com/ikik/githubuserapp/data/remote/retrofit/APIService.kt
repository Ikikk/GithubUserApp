package com.ikik.githubuserapp.data.remote.retrofit

import com.ikik.githubuserapp.data.remote.response.DetailResponse
import com.ikik.githubuserapp.data.remote.response.ItemsItem
import com.ikik.githubuserapp.data.remote.response.SearchResponse
import retrofit2.Call
import retrofit2.http.*

interface APIService {

    @GET("search/users")
    fun getSearchUser(
        @Query("q") query: String
    ): Call<SearchResponse>

    @GET("users/{username}")
    fun getDetail(
        @Path("username") username: String
    ): Call<DetailResponse>

    @GET("users/{username}/followers")
    fun getFollower(
        @Path("username") login: String
    ): Call<List<ItemsItem>>

    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") login: String
    ): Call<List<ItemsItem>>
}