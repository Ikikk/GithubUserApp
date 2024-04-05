package com.ikik.githubuserapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ikik.githubuserapp.R
import com.ikik.githubuserapp.data.remote.response.ItemsItem
import com.ikik.githubuserapp.ui.adapter.UserAdapter
import com.ikik.githubuserapp.ui.viewmodel.ListFavoritesViewModel
import com.ikik.githubuserapp.ui.viewmodel.ViewModelFactory

class ListFavoritesActivity : AppCompatActivity() {
    private val viewModel: ListFavoritesViewModel by viewModels {
        ViewModelFactory.getInstance(application)
    }
    private lateinit var adapter: UserAdapter
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_list_favorites)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        adapter = UserAdapter(emptyList())

        progressBar = findViewById(R.id.progressBar)
        progressBar.visibility = View.VISIBLE

        val recyclerView: RecyclerView = findViewById(R.id.rv_user)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        adapter.setOnClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ItemsItem){
                val intent = Intent(this@ListFavoritesActivity, UserDetailsActivity::class.java).apply {
                    putExtra("USERNAME", data.login)
                }
                startActivity(intent)
            }
        })

        viewModel.getAllFavorites().observe(this) { favorites ->
            progressBar.visibility = View.GONE
            val items = favorites.map { favorite ->
                ItemsItem(
                    login = favorite.username,
                    avatarUrl = favorite.avatarUrl ?: "",
                    followersUrl = "",
                    followingUrl = "",
                    htmlUrl = "",
                    url = favorite.url ?: ""
                )
            }
            adapter.updateUsers(items)
        }
    }
}