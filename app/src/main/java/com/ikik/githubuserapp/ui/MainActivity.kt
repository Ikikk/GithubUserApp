package com.ikik.githubuserapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ikik.githubuserapp.R
import com.ikik.githubuserapp.data.remote.response.ItemsItem
import com.ikik.githubuserapp.data.remote.retrofit.APIConfig
import com.ikik.githubuserapp.data.remote.retrofit.APIService
import com.ikik.githubuserapp.databinding.ActivityMainBinding
import com.ikik.githubuserapp.ui.adapter.UserAdapter
import com.ikik.githubuserapp.ui.viewmodel.MainViewModel

class MainActivity : AppCompatActivity()  {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: UserAdapter
    private lateinit var apiService: APIService
    private lateinit var progressBar: ProgressBar
    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = findViewById(R.id.rv_user)
        recyclerView.layoutManager = LinearLayoutManager(this)
        progressBar = findViewById(R.id.mainProgressBar)
        progressBar.visibility = View.VISIBLE
        adapter = UserAdapter(emptyList())

        adapter.setOnClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ItemsItem){
                val intentToDetail = Intent(this@MainActivity, UserDetailsActivity::class.java).apply {
                    putExtra("USERNAME", data.login)
                }
                startActivity(intentToDetail)
            }
        })

        recyclerView.adapter = adapter

        apiService = APIConfig.getAPIService()

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchBar.inflateMenu(R.menu.favorite_menu)
            searchBar.setOnMenuItemClickListener { menuItem ->
                when(menuItem.itemId) {
                    R.id.menu1 -> {
                        val intent = Intent(this@MainActivity, ListFavoritesActivity::class.java)
                        startActivity(intent)
                        true
                    }
                    R.id.menu2 -> {
                        val intent = Intent(this@MainActivity, SwitchThemeActivity::class.java)
                        startActivity(intent)
                        true
                    }
                    else -> false
                }
            }

            searchView
                .editText
                .setOnEditorActionListener { textView, actionId, event ->
                    searchBar.setText(searchView.text)
                    searchView.hide()
                    searchUsers(searchView.text.toString())
                    Toast.makeText(this@MainActivity, searchView.text, Toast.LENGTH_SHORT).show()
                    true
                }
        }

        viewModel.users.observe(this) { users ->
            progressBar.visibility = View.VISIBLE
            adapter.updateUsers(users)
            progressBar.visibility = View.GONE
        }

        viewModel.isLoading.observe(this) { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        if (savedInstanceState == null) {
            searchUsers("a")
            viewModel.searchUsers("a")
        } else {
            viewModel.restoreSearch()
        }
    }

    private fun searchUsers(query: String) {
        if (query.isEmpty()) {
            progressBar.visibility = View.VISIBLE
            adapter.updateUsers(emptyList())
            progressBar.visibility = View.GONE
            return
        }
        progressBar.visibility = View.VISIBLE
        viewModel.searchUsers(query)
    }
}