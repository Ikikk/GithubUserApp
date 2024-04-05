package com.ikik.githubuserapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.ikik.githubuserapp.R
import com.ikik.githubuserapp.data.local.entity.FavoriteUser
import com.ikik.githubuserapp.data.remote.response.DetailResponse
import com.ikik.githubuserapp.databinding.ActivityUserDetailsBinding
import com.ikik.githubuserapp.ui.adapter.SectionPagerAdapter
import com.ikik.githubuserapp.ui.viewmodel.UserDetailsViewModel
import com.ikik.githubuserapp.ui.viewmodel.ViewModelFactory


class UserDetailsActivity : AppCompatActivity() {

    private lateinit var progressBar: ProgressBar
    private val userDetailsViewModel: UserDetailsViewModel by viewModels {
        ViewModelFactory.getInstance(application)
    }
    private lateinit var binding: ActivityUserDetailsBinding
    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityUserDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val username = intent.getStringExtra("USERNAME") ?: return

        progressBar = findViewById(R.id.userDetailsProgressBar)
        progressBar.visibility = View.VISIBLE

        userDetailsViewModel.fetchUserDetails(username)

        userDetailsViewModel.userDetails.observe(this) { userDetails ->
            userDetails?.let {
                bindUserData(it)
            }
        }

        userDetailsViewModel.getByUsername(username).observe(this) { user ->
            isFavorite = user != null
            updateFavoriteButtonIcon()
        }

        println("sebelum set on click listener activity detail")
        binding.fabFavorite.setOnClickListener {
            println("masuk set on click listener activity detail tapi belum ke toggle")
            toggleFavoriteStatus(username)
        }

        userDetailsViewModel.isLoading.observe(this) { isLoading ->
            progressBar.visibility = if(isLoading) View.VISIBLE else View.GONE
        }

        setupViewPager(username)

        val shareButton: ExtendedFloatingActionButton = findViewById(R.id.fab_share)

        shareButton.setOnClickListener {
            val accUrl = userDetailsViewModel.userDetails.value?.url
            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, accUrl)
                type = "text/plain"
            }

            val chooser = Intent.createChooser(shareIntent, "Bagikan dengan...")
            startActivity(chooser)
        }
    }

    private fun bindUserData(userDetails: DetailResponse) {
        Glide.with(this)
            .load(userDetailsViewModel.userDetails.value?.avatarUrl)
            .circleCrop()
            .into(binding.userAvatarImageView)

        binding.usernameTextView.text = userDetailsViewModel.userDetails.value?.login
        binding.followersTextView.text = getString(R.string.followers, userDetailsViewModel.userDetails.value?.followers)
        binding.followingTextView.text = getString(R.string.following, userDetailsViewModel.userDetails.value?.following)
        binding.nameTextView.text = userDetailsViewModel.userDetails.value?.name
        binding.bioTextView.text = userDetailsViewModel.userDetails.value?.bio
    }

    private fun toggleFavoriteStatus(username: String) {
        if (isFavorite) {
            userDetailsViewModel.removeFavorite(FavoriteUser(username, userDetailsViewModel.userDetails.value?.avatarUrl, userDetailsViewModel.userDetails.value?.url))
            Toast.makeText(this, "User removed from favorites", Toast.LENGTH_SHORT).show()
        } else {
            userDetailsViewModel.addFavorite(FavoriteUser(username, userDetailsViewModel.userDetails.value?.avatarUrl, userDetailsViewModel.userDetails.value?.url))
            Toast.makeText(this, "User added to favorites", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateFavoriteButtonIcon() {
        if (isFavorite) {
            binding.fabFavorite.setImageResource(R.drawable.baseline_favorite_24)
        } else {
            binding.fabFavorite.setImageResource(R.drawable.baseline_favorite_border_24)
        }
    }

    private fun setupViewPager(username: String) {
        val sectionsPagerAdapter = SectionPagerAdapter(this, username)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter

        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }
}