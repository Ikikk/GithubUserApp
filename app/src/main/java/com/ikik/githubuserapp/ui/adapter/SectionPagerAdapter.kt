package com.ikik.githubuserapp.ui.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ikik.githubuserapp.ui.fragment.TabFragment

class SectionPagerAdapter(activity: AppCompatActivity, private var username: String) : FragmentStateAdapter(activity) {
    override fun createFragment(position: Int): Fragment {
        val fragment = TabFragment()
        fragment.arguments = Bundle().apply {
            putInt(TabFragment.ARG_SECTION_NUMBER, position + 1)
            putString(TabFragment.ARG_USERNAME, username)
        }
        return fragment
    }

    override fun getItemCount(): Int {
        return 2
    }
}